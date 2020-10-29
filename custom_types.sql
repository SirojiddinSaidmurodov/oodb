START TRANSACTION;
DROP TYPE if exists Personality cascade;
CREATE TYPE Personality as
(
    "name"      varchar(50),
    bio         varchar(500),
    dateOfBirth date
);

DROP TYPE if exists Artist cascade;
CREATE TYPE Artist as
(
    person     Personality,
    occupation varchar(40)
);

DROP TYPE if exists Actor cascade;
CREATE TYPE Actor as
(
    person Personality,
    "role" varchar(40)
);

DROP TABLE if exists Movie cascade;
CREATE TABLE Movie
(
    id      SERIAL,
    "name"  varchar(250),
    actors  Actor[],
    artists Artist[],
    PRIMARY KEY (id)
);

DROP TABLE if exists Rate cascade;
CREATE TABLE Rate
(
    id           SERIAL,
    movie        bigint,
    userID       bigint,
    "value"      int,
    dateOfChange date default CURRENT_DATE,
    PRIMARY KEY (id),
    FOREIGN KEY (movie) references movie (id),
    FOREIGN KEY (userID) references "user" (id)
);

DROP TABLE if exists "user" cascade;
CREATE TABLE "user"
(
    id           SERIAL,
    name         varchar(40),
    passwordHash varchar(256),
    email        varchar(200),
    PRIMARY KEY (id)
);
COMMIT;