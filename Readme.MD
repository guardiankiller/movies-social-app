
### Requirements

- IntelliJ IDEA (optional)
- Java 17
- MySQL database server with movies-social-app empty database

### Setup
- Make sure you have the correct JAVA_HOME and java.exe on the PATH
- Start the backend server
```dtd
set db_password=softuni
set db_username=root
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