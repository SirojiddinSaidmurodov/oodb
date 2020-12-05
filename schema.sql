/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */
-- Schema built according model in MoviePortal package


DROP TABLE IF EXISTS movie CASCADE;
CREATE TABLE movie
(
    id          BIGSERIAL,
    name        char(255),
    releaseDate date,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS person CASCADE;
CREATE TABLE person
(
    id          BIGSERIAL,
    "name"      char(100),
    dateOfBirth date,
    bio         text,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS actor CASCADE;
CREATE TABLE actor
(
    id        BIGSERIAL,
    role      char(255),
    person_id BIGINT,
    movie_id  BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (person_id) REFERENCES person (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (movie_id) REFERENCES movie (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

DROP TABLE IF EXISTS artist CASCADE;
CREATE TABLE artist
(
    id         BIGSERIAL,
    occupation char(255),
    person_id  BIGINT,
    movie_id   BIGINT,
    PRIMARY KEY (id),
    FOREIGN KEY (person_id) REFERENCES person (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (movie_id) REFERENCES movie (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

DROP TABLE IF EXISTS "user" CASCADE;
CREATE TABLE "user"
(
    id    BIGSERIAL,
    name  char(255),
    eMail char(25),
    passwordHash char(50),
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS rate CASCADE;
CREATE TABLE rate
(
    id           BIGSERIAL,
    value        integer,
    movie_id     bigint,
    dateOfChange date,
    user_id       bigint,
    PRIMARY KEY (id),
    FOREIGN KEY (movie_id) REFERENCES movie (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES "user" (id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);