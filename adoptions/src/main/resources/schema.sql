CREATE TABLE IF NOT EXISTS dog (
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    owner       VARCHAR(255),
    description TEXT
);
