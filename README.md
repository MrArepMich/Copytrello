# Copytrello

Copytrello is a multi-user task planner that provides user registration and authentication features, task management, and email notification sending. The project utilizes technologies such as Kafka, Docker, and Spring Boot. It is built on a microservices architecture and uses Docker and Kafka for service interaction.

## Key Features

- User registration and authentication using JWT for security.
- Creating, editing, and deleting tasks with the ability to add descriptions and statuses.
- Email notifications for new users and task updates.
- Integration with Kafka for processing and sending email tasks.

## Technology Stack

- **Backend**: Java, Spring Boot, Spring Security, Spring Mail, JWT, Kafka, Mailjet
- **Frontend**: JavaScript, HTML, CSS
- **Database**: PostgreSQL
- **Containerization**: Docker
- **Additional Tools**: Gradle, Redis, Flyway

## Installation and Running

1. Clone the repository:

    ```bash
    git clone https://github.com/Repinskie/Copytrello.git
    ```

2. Navigate to the project directory:

    ```bash
    cd "yourPathToCopytrello"/Copytrello
    ```

3. Ensure that Docker is installed.

4. In the root of the project, copy `.env.example` to `.env` and configure the database credentials, JWT secret, and Mailjet API keys:

    ```plaintext
    POSTGRES_USER=yourPostgresUser
    POSTGRES_PASSWORD=yourPostgresPassword
    POSTGRES_DB=yourPostgresDbName

    # application connection settings
    DB_URL=jdbc:postgresql://postgresql:5432/copytrello_db
    DB_USERNAME=yourPostgresUser
    DB_PASSWORD=yourPostgresPassword

    # JWT configuration (used by `application-jwt.yml`)
    JWT_SECRET_KEY=yourJwtSecretKey
    MAILjET_API_KEY=yourMailjetApiKey
    MAILjET_SECRET_KEY=yourMailjetSecretKey
    ```

    The backend service now reads JWT settings from `src/main/resources/application-jwt.yml`,
    so be sure to provide a strong value for `JWT_SECRET_KEY`.

    To obtain Mailjet keys, register on the official [Mailjet website](https://www.mailjet.com/). Go to the API section and generate a SECRET KEY. Also, in the email-sender configuration file, replace the email address with the one you registered with Mailjet.

5. Start the project using Docker Compose:

    ```bash
    docker-compose up --build
    ```

## Usage

After starting the application, open your browser and go to `http://localhost:8300/login.html` to access the application interface or test it via Postman.

## Main Microservices

The project consists of several microservices:

- **Backend Service** — manages the business logic of the application and processes requests to create and update tasks.
- **Email Sender Service** — sends email notifications for new users and task updates, using Kafka for inter-service communication.
- **Scheduler** — responsible for statistics on completed and uncompleted tasks and generating this statistics for email sending.
