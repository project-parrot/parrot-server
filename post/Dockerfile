FROM gradle:8.14.3-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle :post:bootJar --no-daemon

FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/post/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]