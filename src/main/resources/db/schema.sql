CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS "users"
(
    id         uuid PRIMARY KEY   DEFAULT gen_random_uuid(),
    name       text      NOT NULL,
    skills     text[]    NOT NULL,
    experience integer   NOT NULL,
    created_at timestamp NOT NULL DEFAULT now()
);

CREATE TABLE IF NOT EXISTS "jobs"
(
    id         uuid PRIMARY KEY   DEFAULT gen_random_uuid(),
    title      text      NOT NULL,
    company    text      NOT NULL,
    tags       text[]    NOT NULL,
    experience integer   NOT NULL,
    created_at timestamp NOT NULL DEFAULT now()
);