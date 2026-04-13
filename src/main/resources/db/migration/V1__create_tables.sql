-- V1__create_tables.sql
-- Creates the core tables for the Biblioteca API

CREATE TABLE IF NOT EXISTS authors (
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    nationality VARCHAR(100),
    created_at  TIMESTAMP NOT NULL DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS books (
    id          SERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    isbn        VARCHAR(20) UNIQUE,
    year        INTEGER,
    author_id   INTEGER NOT NULL REFERENCES authors(id) ON DELETE CASCADE,
    created_at  TIMESTAMP NOT NULL DEFAULT NOW()
);
