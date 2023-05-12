
-- CREAR TYPES

CREATE TYPE user_type AS(
    id_user INTEGER,
    user_nick VARCHAR,
    user_email VARCHAR,
    user_password VARCHAR,
    user_photo VARCHAR,
    user_level VARCHAR,
    user_role VARCHAR
);

CREATE TYPE treasure_type AS(
    treasure_id INTEGER,
    treasure_name VARCHAR,
    treasure_description VARCHAR,
    treasure_latitude NUMERIC,
    treasure_longitude NUMERIC,
    treasure_image VARCHAR,
    treasure_location VARCHAR,
    treasure_clue VARCHAR,
    treasure_status VARCHAR,
    treasure_difficulty VARCHAR,
    treasure_score NUMERIC
);

CREATE TYPE game_type AS(
    game_id INTEGER,
    id_user INTEGER,
    treasure_id INTEGER,
    game_time_start VARCHAR,
    game_time_end VARCHAR,
    game_image VARCHAR,
    game_solved BOOLEAN
);

CREATE TYPE report_type AS(
    report_id INTEGER,
    id_user INTEGER,
    treasure_id INTEGER,
    report_info VARCHAR,
    report_date VARCHAR
);

CREATE TYPE favourite_type AS(
    id_user INTEGER,
    treasure_id INTEGER
);

CREATE TYPE review_type AS (
    id_review INTEGER,
    id_treasure INTEGER,
    id_user INTEGER,
    review_opinion VARCHAR,
    review_rating VARCHAR,
    review_photo VARCHAR
);

-- CREAR TAULES A PARTIR DELS TYPES

CREATE TABLE users OF user_type (
    PRIMARY KEY (id_user)
);

CREATE TABLE treasure OF treasure_type (
    PRIMARY KEY (treasure_id)
);

CREATE TABLE game OF game_type (
    PRIMARY KEY (game_id),
    FOREIGN KEY (id_user) REFERENCES users(id_user),
    FOREIGN KEY (treasure_id) REFERENCES treasure(treasure_id)
);

CREATE TABLE report OF report_type (
    PRIMARY KEY (report_id),
    FOREIGN KEY (id_user) REFERENCES users(id_user),
    FOREIGN KEY (treasure_id) REFERENCES treasure(treasure_id)
);

CREATE TABLE favourite OF favourite_type (
    FOREIGN KEY (id_user) REFERENCES users(id_user),
    FOREIGN KEY (treasure_id) REFERENCES treasure(treasure_id)
);

CREATE TABLE review OF review_type (
    PRIMARY KEY (id_review),
    FOREIGN KEY (id_treasure) REFERENCES treasure(treasure_id),
    FOREIGN KEY (id_user) REFERENCES users(id_user)
);

-- CREAR SEQUENCES PER LES CLAUS PRIMÀRIES

CREATE SEQUENCE id_user_seq 
START 1
INCREMENT 1;

CREATE SEQUENCE treasure_id_seq
START 1
INCREMENT 1;

CREATE SEQUENCE game_id_seq
START 1
INCREMENT 1;

CREATE SEQUENCE report_id_seq
START 1
INCREMENT 1;

CREATE SEQUENCE id_review_seq
START 1
INCREMENT 1;