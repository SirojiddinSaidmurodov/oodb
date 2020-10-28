DROP TYPE if exists Personality;
CREATE TYPE Personality as
(
    "name"      varchar(50),
    bio         varchar(250),
    dateOfBirth date
);

DROP TYPE if exists Artist;
CREATE TYPE Artist as
(
    person     Personality,
    occupation varchar(40)
);

DROP TYPE if exists Actor;
CREATE TYPE Actor as
(
    person Personality,
    "role" varchar(40)
);

DROP TABLE if exists Movie;
CREATE TABLE Movie
(
    "name"     varchar(250),
    actors     Actor[],
    rate       int,
    ratesCount int
);

DROP TYPE if exists Rate;
CREATE TYPE Rate as
(
    movie        int,
    "value"      int,
    dateOfChange date
);

DROP TABLE if exists "User";
CREATE TABLE "User"
(
    name         varchar(40),
    passwordHash varchar(256),
    userRate     Rate[],
    email        varchar(200)
);