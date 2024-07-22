FROM openjdk:17-alpine AS builder
WORKDIR /app
COPY . .
RUN ./gradlew clean build --no-daemon -x test

FROM openjdk:17-alpine
WORKDIR /app
COPY --from=builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]








