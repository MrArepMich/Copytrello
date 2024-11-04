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

4. In the root of the project, create a `.env` file and configure the environment variables for connecting to the database and API keys for Mailjet:

    ```plaintext
    POSTGRES_USER=yourPostgresUser
    POSTGRES_PASSWORD=yourPostgresPassword
    POSTGRES_DB=yourPostgresDbName

    JWT_SECRET_KEY=yourJwtSecretKey
    COPYTRELLO_MAILJET_API_KEY=yourMailjetApiKey
    COPYTRELLO_MAILJET_SECRET_KEY=yourMailjetSecretKey
    ```

    To obtain Mailjet keys, register on the official Mailjet website. Go to the API section and generate a SECRET KEY. Also, in the email-sender configuration file, replace the email address with the one you registered with Mailjet.

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
