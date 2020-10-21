DROP TABLE if exists Actors;
DROP TYPE if exists Personality;
CREATE TYPE Personality AS
(
    name        varchar(40),
    dateOfBirth date,
    bio         varchar(200)
);
CREATE TABLE Actors
(
    id     SERIAL,
    person Personality,
    role   char(40)
);