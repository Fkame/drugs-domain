--liquibase formatted sql

--changeset fkame:created_drug
CREATE TABLE IF NOT EXISTS drug (
    id BIGSERIAL PRIMARY KEY,
    drug_name TEXT NOT NULL,
    prod_country TEXT,
    prod_company TEXT,
    release_form TEXT,
    purpose TEXT,
    needRecipe BOOLEAN,
    manual_ref TEXT,
    update_time TIMESTAMP WITH TIME ZONE
);

--changeset fkame:index_on_name
CREATE INDEX drug_name_idx ON drug(drug_name);