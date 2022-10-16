--liquibase formatted sql

--changeset fkame:created_medicine_in_store
CREATE TABLE IF NOT EXISTS medicine_in_store (
    id BIGSERIAL PRIMARY KEY,
    drug_id BIGINT REFERENCES drug(id),
    store_id BIGINT REFERENCES drugstore(id),
    price NUMERIC
);