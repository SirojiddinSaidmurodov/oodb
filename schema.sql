/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */
-- Schema built according model in MoviePortal package


DROP TABLE IF EXISTS personality;
CREATE TABLE personality
(
    id          SERIAL,
    "name"      char(100),
    dateOfBirth date,
    bio         text,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS actor;
CREATE TABLE actor
(
    id        SERIAL,
    person_id BIGINT,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS artist;
CREATE TABLE artist
(
    id        SERIAL,
    person_id BIGINT,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS movie;
CREATE TABLE movie
(
    id          SERIAL,
    name        char(255),
    releaseDate date,
    PRIMARY KEY (id)
);

DROP TABLE IF EXISTS usr;
CREATE TABLE usr
(
    name  char(255),
    eMail char(25)
);