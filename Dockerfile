# Stage 1: Build the JAR using Gradle 9.7.1
FROM gradle:9.7.1-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle bootJar -x test

# Stage 2: Run the JAR using Amazon Corretto 21 (JRE-only runtime image)
FROM amazoncorretto:21
WORKDIR /app

# Copy the built JAR
COPY --from=build /app/build/libs/*.jar app.jar

# Copy the docs folder for file:-based RAG ingestion
COPY --from=build /app/src/main/resources/docs /app/docs

# Expose the port
EXPOSE 8080

# Run the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
