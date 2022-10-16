--liquibase formatted sql

--changeset fkame:created_substance
CREATE TABLE IF NOT EXISTS substance (
    id BIGSERIAL PRIMARY KEY,
    substance_name TEXT
);