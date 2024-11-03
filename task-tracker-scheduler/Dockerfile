FROM gradle:jdk21-alpine AS builder

WORKDIR /app

COPY build.gradle ./

COPY /src ./src

RUN gradle clean build -x test --no-daemon

FROM eclipse-temurin:21-jre-alpine
RUN adduser --system app-user

COPY --from=builder --chown=app-user:app-user /app/build/libs/*.jar app.jar
EXPOSE 8080

USER app-user

CMD ["java", "-jar", "app.jar"]

LABEL authors="Michael"
