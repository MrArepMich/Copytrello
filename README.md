# Copytrello

# Copytrello

**Copytrello** is a multi-user task planner that provides functionality for user registration and authentication, task management, and sending email notifications. This project utilizes technologies such as Kafka, Docker, and Spring Boot. It is built on a microservices architecture and uses Docker and Kafka for service interaction.

### 📌 Project Services

Copytrello consists of several services:
- **Backend**: handles user registration and authentication using JWT, as well as task management.
- **Email Sender**: responsible for sending email notifications about registration and task updates.
- **Scheduler**: at the end of the day, it compiles a list of completed and uncompleted tasks for the user and prepares an email for sending.

### ⚙️ Technologies Used

- **Programming Language**: Java
- **Build Tool**: Gradle
- **Framework**: Spring Boot (including Spring Security, Spring Kafka, Spring Scheduler, Spring Mail)
- **Authentication**: JWT
- **Database**: PostgreSQL with Spring Data JPA
- **Migrations**: Flyway
- **Frontend**: HTML/CSS, Bootstrap, JavaScript, Ajax
- **Infrastructure**: Docker and microservices
- **Message Broker**: Kafka

