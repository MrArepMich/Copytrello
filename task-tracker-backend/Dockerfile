# Стадия сборки
FROM gradle:jdk21-alpine AS builder

# Рабочая директория
WORKDIR /app

# Копируем весь проект для сборки одного модуля
COPY . .

# Собираем только backend без тестов
RUN gradle :task-tracker-backend:clean :task-tracker-backend:build -x test --no-daemon

# Стадия выполнения
FROM eclipse-temurin:21-jre-alpine

# Создаём системного пользователя с явной группой
RUN addgroup app-user && adduser --system --no-create-home -G app-user app-user

# Создаём директорию для логов
RUN mkdir -p /logs && chown app-user:app-user /logs

# Копируем собранный JAR из стадии сборки
COPY --from=builder --chown=app-user:app-user /app/build/libs/*.jar /app/app.jar

# Экспонируем порт приложения
EXPOSE 8300

# Переключаемся на системного пользователя
USER app-user

# Команда для запуска приложения
CMD ["java", "-jar", "/app/app.jar"]

# Добавляем метаданные
LABEL authors="Michael"
