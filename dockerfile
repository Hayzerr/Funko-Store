# Stage 1: Build the application
FROM openjdk:21-jdk AS build
WORKDIR /app

# Copy Gradle wrapper and build files
COPY build.gradle.kts settings.gradle.kts gradlew ./
COPY gradle gradle

# Set execution permission for the Gradle wrapper
RUN chmod +x ./gradlew

# Copy the source code
COPY src src

# Build the application without running tests
RUN ./gradlew clean build -x test

# Stage 2: Create the final Docker image using OpenJDK 21
FROM openjdk:21-jdk
VOLUME /tmp

# Copy the JAR from the build stage
COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080
