FROM gradle:8.14.3-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle :media:bootJar --no-daemon

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/media/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]