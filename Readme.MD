
# Movies Social App

## Definition
The Movies Social App is an application to share and comment on movies:
 - Login & Register a user
 - Browse movies
 - Change languages from English to Bulgarian and vice versa

## Preview images

<img src="https://github.com/guardiankiller/movies-social-app/blob/main/preview/1image.PNG?raw=true" width="800"/>
<img src="https://github.com/guardiankiller/movies-social-app/blob/main/preview/2image.jpg?raw=true" width="800"/>
<img src="https://github.com/guardiankiller/movies-social-app/blob/main/preview/3image.jpg?raw=true" width="800"/>
<img src="https://github.com/guardiankiller/movies-social-app/blob/main/preview/4image.PNG?raw=true" width="800"/>

## App Details
- This app is written in `Spring Boot`. It uses `Spring Security` for authentication. Authentication is with JWT 
  tokens.
- The backend uses `Spring Data JPA` with `Liquibase` and it uses a `MySQL` database.
- Input data is automatically validated with `Hibernate validator` and custom validations are included.
- Uses the `TMDB API` to import movies.
- The import functionality is reactive and uses the `Spring Web Flux` rest client.
- Exports metrics using `Spring Actuator`.
- The `frontend` is built with `React` and `Vite`.
- `Gradle` manages the `Node.js` environment for the `frontend` part.
- The final `jar` file is a `fat jar` and `Gradle` embeds the generated `html`, `css` and `js` files from the `vite 
build`.
- The `fat jar` is bundled in a `docker image` and the image is published to `Github` packages with `Github Actions`.
- Included `CI/CD` using `Github Actions`.

## RESTful API
The following endpoints are supported:

`Users` endpoints:
  - `POST /api/users?dryRun=true|false` - Adds additional roles to the user
  - `GET /api/users` - lists users
  - `GET /api/users/:useername` - Retrieves a single user given their username
  - `PUT /api/users/:useername` - Updates the user information based on the provided information
  - `DELETE /api/users/:useername` - Deletes the user given their username
  - `POST /api/users/:useername/roles` - Adds additional roles to the user
  - `DELETE /api/users/:useername/roles` - Removes roles from the user

`Authetication` endpoints:
 - `POST /api/auth` - generates an access token

`Genres` endpoints:
 - `GET /api/genres?language=EN|BG` - Lists all `genres` registered on the server.
 - `GET /api/genres/:id?language=EN|BG` - Lists information about a particular `genre`.
 - `GET /api/genres/:id/movies?language=EN|BG&page=1&limit=30` - Lists all movies for the `genre` with the given 
   `id`. This endpoint supports `paging`.

`Movies` endpoints:
- `POST /api/movies` - Imports movies from `TMDB API` 
- `GET /api/movies?language=EN|BG&page=1&limit=30` - This endpoint supports `paging`.
- `GET /api/movies/:id?language=EN|BG` - Gives more detailed information about a single movie given its `id`.

Utility endpoints:
- `GET /api/languages` - Lists all available languages that the server supports. To request content in a particular 
  language use the response values from this endpoint as input for `language` query parameters.
- `GET /api/settings` - Checks if the server is still on initial loading
## Requirements
- IntelliJ IDEA (optional)
- Java 17
- MySQL database server with movies-social-app empty database

## Setup
- Generate an API key from TMDB (Create an account then go to Settings > API and copy the 'API Read Access Token')
- Make sure you have the correct JAVA_HOME and java.exe on the PATH
- Start the backend server
```dtd
set db_password=softuni
set db_username=root
set tmdb_api_key=<tmdb_api_secret>
set db_url=jdbc:mysql://localhost:3306/movies-social-app (if the database server is on localhost:3306 and has movies-social-app skip this env)
gradlew bootRun
```

- Start the frontend dev server (gradle way)
```dtd
gradlew :frontend:viteDev
```

- Start the frontend dev server (npm way)
```dtd
cd frontend
env.bat
npm run dev
```

## Run integration tests with coverage
```dtd
set tmdb_api_key=<tmdb_api_secret>
gradlew :jacocoTestReport
```
The jacoco test coverage is in `build\reports\jacoco\test\html\index.html`
