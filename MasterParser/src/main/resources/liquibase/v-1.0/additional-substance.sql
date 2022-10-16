--liquibase formatted sql

--changeset fkame:created_additional_substance
CREATE TABLE IF NOT EXISTS additional_substance (
    id BIGSERIAL PRIMARY KEY,
    substance_id BIGINT REFERENCES substance(id),
    drug_id BIGINT REFERENCES drug(id)
);