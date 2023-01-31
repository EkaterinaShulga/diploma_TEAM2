-- liquibase formatted sql

--changeset iaktov:3
DROP TABLE NewPassword;
DROP TABLE RegisterReq;
DROP TABLE LoginReq;
DROP TABLE CreateAds;
DROP TABLE ResponseWrapperAds;
DROP TABLE ResponseWrapperComment;
DROP TABLE FullAds;


--changeset iaktov:2
CREATE TABLE Users
(
    email     TEXT NOT NULL,
    firstName TEXT NOT NULL,
    id        BIGSERIAL PRIMARY KEY,
    lastName  TEXT NOT NULL,
    phone     TEXT NOT NULL,
    regDate   TEXT NOT NULL,
    city      TEXT NOT NULL,
    image     TEXT NOT NULL

);
CREATE TABLE NewPassword
(
    currentPassword TEXT NOT NULL,
    newPassword     TEXT NOT NULL
);

CREATE TABLE RegisterReq
(
    userName  TEXT NOT NULL,
    password  TEXT NOT NULL,
    firstName TEXT NOT NULL,
    lastName  TEXT NOT NULL,
    phone     TEXT NOT NULL,
    role      TEXT
);

CREATE TABLE LoginReq
(
    password TEXT NOT NULL,
    username TEXT NOT NULL

);

CREATE TABLE CreateAds
(
    description TEXT   NOT NULL,
    price       BIGINT NOT NULL,
    title       TEXT   NOT NULL

);

CREATE TABLE Ads
(
    author BIGINT NOT NULL,
    image  TEXT   NOT NULL,
    pk     BIGSERIAL PRIMARY KEY,
    price  BIGINT NOT NULL,
    title  TEXT   NOT NULL

);

CREATE TABLE Comment
(
    author    BIGINT NOT NULL,
    createdAt TEXT   NOT NULL,
    pk        BIGSERIAL PRIMARY KEY,
    text      TEXT   NOT NULL,
    ad        INT,
    FOREIGN KEY (ad) REFERENCES Ads (pk)
);

CREATE TABLE ResponseWrapperAds
(
    count   BIGINT NOT NULL,
    results BIGSERIAL REFERENCES Comment (pk)
);
CREATE TABLE FullAds
(
    authorFirstName TEXT   NOT NULL,
    authorLastName  TEXT   NOT NULL,
    description     TEXT   NOT NULL,
    email           TEXT   NOT NULL,
    image           TEXT   NOT NULL,
    phone           TEXT   NOT NULL,
    pk              BIGSERIAL PRIMARY KEY,
    price           BIGINT NOT NULL,
    title           TEXT   NOT NULL

);

CREATE TABLE ResponseWrapperComment
(
    count   BIGINT NOT NULL,
    results BIGSERIAL REFERENCES Comment (pk)
);

