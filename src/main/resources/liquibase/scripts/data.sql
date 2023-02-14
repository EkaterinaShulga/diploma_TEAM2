-- liquibase formatted sql

--changeset iaktov:2
CREATE TABLE Users
(
    email     TEXT,
    firstName TEXT,
    id        bigint primary key,
    lastName  TEXT,
    phone     TEXT,
    regDate   TEXT,
    city      TEXT

);

CREATE TABLE Ads
(
    author BIGSERIAL,
    image  bytea,
    pk     bigint PRIMARY KEY,
    price  BIGINT,
    title  TEXT,
    FOREIGN KEY (author) REFERENCES Users (id)

);

CREATE TABLE Comment
(
    author     bigint,
    created_at TEXT,
    pk         bigint PRIMARY KEY,
    text       TEXT,
    ads_pk     BIGSERIAL,
    FOREIGN KEY (author) REFERENCES Ads (author),
    FOREIGN KEY (ads_pk) REFERENCES Ads (pk) ON DELETE CASCADE

);

create table Images
(
    fileSize  bigint,
    mediaType varchar(255),
    image     bytea,
    id        bigint PRIMARY KEY,
    ads_pk    BIGSERIAL,
    FOREIGN KEY (ads_pk) REFERENCES Ads (pk) ON DELETE CASCADE
)

--changeset shulga:3
create table if not exists authorities (
    username  varchar(255) primary key,
    authority varchar(32)
    );

alter table Users add column enabled boolean;
create sequence users_id_seq;
alter table Users alter column id set default nextval('users_id_seq');
alter sequence users_id_seq OWNED BY Users.id;


