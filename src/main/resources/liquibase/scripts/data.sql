-- liquibase formatted sql

--changeset iaktov:1
CREATE TABLE Users
(
    email     TEXT ,
    firstName TEXT ,
    id        bigint primary key ,
    lastName  TEXT ,
    phone     TEXT ,
    regDate   TEXT ,
    city      TEXT ,
    image     TEXT
);


CREATE TABLE NewPassword
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

CREATE TABLE Comments
(
    author    BIGINT,
    createdAt TEXT,
    pk        BIGSERIAL PRIMARY KEY,
    text      TEXT,
    ad        INT,
    FOREIGN KEY (ad) REFERENCES Ads (pk)
);

CREATE TABLE ResponseWrapperAds
(
    count   BIGINT,
    results BIGSERIAL REFERENCES Comments (pk)
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
    count   BIGINT ,
    results BIGSERIAL REFERENCES Comments (pk)
);

--changeset iaktov:2
DROP TABLE NewPassword;
DROP TABLE RegisterReq;
DROP TABLE LoginReq;
DROP TABLE CreateAds;
DROP TABLE ResponseWrapperAds;
DROP TABLE ResponseWrapperComment;
DROP TABLE FullAds;

--changeset anmalashenko:3
alter table Users add column enabled boolean;
create sequence users_id_seq;
alter table Users alter column id set default nextval('users_id_seq');
alter sequence users_id_seq OWNED BY Users.id;

create table if not exists authorities (
                                           username  varchar(255) not null primary key,
                                           authority varchar(32)
);
