-- liquibase formatted sql

--changeset iaktov:2
CREATE TABLE Users
(
    id        BIGSERIAL PRIMARY KEY,
    email     TEXT,
    firstName TEXT,
    lastName  TEXT,
    phone     TEXT,
    regDate   TEXT,
    city      TEXT,
    image     TEXT

);

--changeset iaktov: 1
CREATE TABLE Password
(
    currentPassword TEXT,
    newPassword     TEXT
);

CREATE TABLE RegisterReq
(
    userName  TEXT,
    password  TEXT,
    firstName TEXT,
    lastName  TEXT,
    phone     TEXT,
    role      TEXT
);

CREATE TABLE LoginReq
(
    password TEXT,
    username TEXT

);

CREATE TABLE CreateAds
(
    description TEXT,
    price       BIGINT,
    title       TEXT

);

CREATE TABLE Ads
(
    author BIGINT,
    image  TEXT,
    pk     BIGSERIAL PRIMARY KEY,
    price  BIGINT,
    title  TEXT
);

CREATE TABLE Comment
(
    author    BIGINT,
    createdAt TEXT,
    pk        BIGSERIAL PRIMARY KEY,
    text      TEXT
);


CREATE TABLE ResponseWrapperAds
(
    count   BIGINT,
    results BIGSERIAL REFERENCES Comment (pk)
);
CREATE TABLE FullAds
(
    authorFirstName TEXT,
    authorLastName  TEXT,
    description     TEXT,
    email           TEXT,
    image           TEXT,
    phone           TEXT,
    pk              BIGSERIAL PRIMARY KEY,
    price           BIGINT,
    title           TEXT

);

CREATE TABLE ResponseWrapperComment
(
    count   BIGINT,
    results BIGSERIAL REFERENCES Comment (pk)
);



