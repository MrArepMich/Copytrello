CREATE SCHEMA IF NOT EXISTS tasks;
SET schema 'tasks';

CREATE TABLE tasks
(
    id           SERIAL PRIMARY KEY,
    title        VARCHAR(255) NOT NULL,
    description  TEXT,
    owner_id     BIGINT NOT NULL,
    status       VARCHAR(20) DEFAULT 'in progress' NOT NULL,
    completed_at TIMESTAMP,
    CONSTRAINT fk_owner_id FOREIGN KEY (owner_id) REFERENCES users.users (id)
);
