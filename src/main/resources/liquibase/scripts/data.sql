-- liquibase formatted sql

--changeset iaktov:2
CREATE TABLE Ads
(
    author BIGINT NOT NULL,
    image  TEXT[]   NOT NULL,
    pk     BIGSERIAL PRIMARY KEY,
    price  BIGINT NOT NULL,
    title  TEXT   NOT NULL
);

CREATE TABLE Comments
(
    author    BIGINT NOT NULL,
    created_at TEXT   NOT NULL,
    pk        BIGSERIAL PRIMARY KEY,
    text      TEXT   NOT NULL,
    ads_pk    BIGINT NOT NULL
);

