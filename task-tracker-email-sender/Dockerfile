FROM gradle:jdk21-alpine AS builder

WORKDIR /app

# Копируем весь проект для сборки модуля
COPY . .

# Собираем только email-sender без тестов
RUN gradle :task-tracker-email-sender:clean :task-tracker-email-sender:build -x test --no-daemon

FROM eclipse-temurin:21-jre-alpine
RUN adduser --system app-user

COPY --from=builder --chown=app-user:app-user /app/task-tracker-email-sender/build/libs/*.jar app.jar
EXPOSE 8080 5005

USER app-user

CMD ["java", "-jar", "app.jar"]

LABEL authors="Michael"
