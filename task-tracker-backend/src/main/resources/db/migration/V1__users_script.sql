CREATE SCHEMA IF NOT EXISTS users;
SET schema 'users';

CREATE TABLE roles
(
    id    bigserial PRIMARY KEY,
    title varchar NOT NULL
);


CREATE TABLE users
(
    id         bigserial PRIMARY KEY,
    email      varchar(255) NOT NULL,
    password   varchar(255) NOT NULL
);

CREATE TABLE users_roles
(
    user_id bigint NOT NULL REFERENCES users (id),
    role_id bigint NOT NULL REFERENCES roles (id),
    PRIMARY KEY (user_id, role_id)
);

INSERT INTO roles (title)
VALUES ('ROLE_USER'),
       ('ROLE_ADMIN');

INSERT INTO users (email, password)
VALUES ('user@gmail.com', '$2a$04$ekqRbyAkWuSKu3Wh1kBVhethtsDjyeh1E6JiJu6p4.bvSYXk1jKG.'),
       ('doctor@gmail.com', '$2a$04$BTRACNBSZ3NDNDFysl99hekwNt93/F6Ld4vv3K9tyjO3RGLcxwlDy'),
       ('admin@gmail.com', '$2a$04$iTSRnZLeZyHMMFJ6YoGO3OPEl10ThcRpZIIweZsEdwuwsjOr.rzwS');

INSERT INTO users_roles (user_id, role_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (3, 2)

