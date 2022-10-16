--liquibase formatted sql

--changeset fkame:created_active_substance
CREATE TABLE IF NOT EXISTS active_substance (
    id BIGSERIAL PRIMARY KEY,
    substance_id BIGINT REFERENCES substance(id),
    drug_id BIGINT REFERENCES drug(id)
);