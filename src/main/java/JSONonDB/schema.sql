DROP table if exists person;
DROP table if exists personb;
CREATE table person
(
    id       serial,
    content  json
);
CREATE table personb
(
    id       serial,
    content  jsonb
);