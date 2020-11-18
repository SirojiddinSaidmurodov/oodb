/*
 * Copyright (c) 2020. Saidmurodov Sirojiddin
 * siroj.serj15@outlook.com
 * All rights reserved.
 */

DROP table if exists person;
DROP table if exists personb;
CREATE table person
(
    id      serial,
    content json
);
CREATE table personb
(
    id      serial,
    content jsonb
);