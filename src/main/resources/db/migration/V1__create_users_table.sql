CREATE TYPE user_role AS ENUM ('CUSTOMER', 'SELLER', 'AGENT', 'ADMIN');
CREATE TYPE user_status AS ENUM ('ACTIVE', 'DISABLED');

CREATE TABLE users (
                       id            BIGSERIAL PRIMARY KEY,
                       name          VARCHAR(150)  NOT NULL,
                       email         VARCHAR(150)  NOT NULL UNIQUE,
                       password      VARCHAR(255)  NOT NULL,
                       phone         VARCHAR(20),
                       role          user_role     NOT NULL DEFAULT 'CUSTOMER',
                       status        user_status   NOT NULL DEFAULT 'ACTIVE',
                       created_at    TIMESTAMP     NOT NULL DEFAULT now(),
                       updated_at    TIMESTAMP     NOT NULL DEFAULT now()
);

CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role  ON users(role);
