FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build.gradle settings.gradle ./
COPY gradle ./gradle
COPY gradlew ./

RUN chmod +x gradlew

RUN ./gradlew dependencies --no-daemon

COPY src ./src

RUN ./gradlew bootJar --no-daemon

EXPOSE 8080
CMD ["sh", "-c", "java -jar build/libs/*.jar"]