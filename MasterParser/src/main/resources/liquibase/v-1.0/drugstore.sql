--liquibase formatted sql

--changeset fkame:created_drugstore
CREATE TABLE IF NOT EXISTS drugstore (
    id BIGSERIAL PRIMARY KEY,
    store_name TEXT NOT NULL,
    url TEXT NOT NULL
);

--changeset fkame:add_Planeta_Zdorovia
INSERT INTO drugstore (store_name, url)
    VALUES ('Планета Здоровья', 'https://planetazdorovo.ru/');