# Stage 1: build with Gradle (already has gradle + JDK21)
FROM gradle:8.8-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle clean build -x test

# Stage 2: runtime
FROM eclipse-temurin:21-jre
WORKDIR /
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
