FROM alpine:latest

ADD build/libs/movies-social-app-1.0.*.jar app.jar

EXPOSE 8080

RUN apk add openjdk17-jre-headless
ENTRYPOINT [ "java", "-jar", "/app.jar" ]
