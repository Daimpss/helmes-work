FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY gradlew ./

RUN chmod +x gradlew

# Download dependencies (this layer will be cached if build.gradle doesn't change)
RUN ./gradlew dependencies --no-daemon

# Copy source code
COPY src ./src

# Build the application
RUN ./gradlew bootJar --no-daemon

# Expose port 8080
EXPOSE 8080

CMD ["sh", "-c", "java -jar build/libs/*.jar"]