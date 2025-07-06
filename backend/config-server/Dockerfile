FROM openjdk:17-jdk-slim

WORKDIR /app

# Copy Maven wrapper and pom.xml first
COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .

# Make mvnw executable
RUN chmod +x ./mvnw

# Download dependencies (this layer will be cached)
RUN ./mvnw dependency:go-offline -B

# Copy source code
COPY src ./src

# Default command (can be overridden by docker-compose)
CMD ["./mvnw", "spring-boot:run"]

EXPOSE 8888