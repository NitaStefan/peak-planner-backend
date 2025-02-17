# Build Stage
FROM maven:3.9.5-eclipse-temurin-21 as build
WORKDIR /app

# Copy project files
COPY . .

# Build the application (skipping tests)
RUN mvn clean package -DskipTests

# Run Stage
FROM eclipse-temurin:21-jdk-slim
WORKDIR /app

# Copy the built JAR file from the build stage
COPY --from=build /app/target/peak-planner-0.0.1-SNAPSHOT.jar app.jar

# Expose port 8080 for Render
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
