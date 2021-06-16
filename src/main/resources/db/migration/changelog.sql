-- liquibase formatted sql

-- changeset anna:11
DROP TABLE IF EXISTS well;
CREATE TABLE IF NOT EXISTS well
(
    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `name` VARCHAR(32) NOT NULL UNIQUE
);

DROP TABLE IF EXISTS equipment;
CREATE TABLE IF NOT EXISTS equipment
(
    `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    `name` VARCHAR(32) NOT NULL UNIQUE,
    `Well_id` INTEGER,
    FOREIGN KEY (`Well_id`)
        REFERENCES well(id)
        ON DELETE CASCADE
);

-- mvn liquibase:update