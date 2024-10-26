# Use the official OpenJDK 17 image
FROM openjdk:17-jdk-slim AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the pom.xml and the src directory to the working directory
COPY pom.xml .
COPY src ./src

# Install Maven and package the application
RUN apt-get update && apt-get install -y maven
RUN mvn clean package -DskipTests

# Use a smaller image for the final application
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the packaged jar from the builder image
COPY --from=builder /app/target/book_store-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 8080

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
