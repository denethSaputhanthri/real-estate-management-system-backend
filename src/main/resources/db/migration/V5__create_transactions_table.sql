CREATE TYPE transaction_type AS ENUM (
    'SALE',
    'RENT'
);

CREATE TYPE payment_status_enum AS ENUM (
    'PENDING',
    'PARTIAL',
    'PAID'
);

CREATE TABLE transactions (
                              id                BIGSERIAL PRIMARY KEY,
                              property_id       BIGINT               NOT NULL REFERENCES properties(id) ON DELETE RESTRICT,
                              buyer_id          BIGINT               NOT NULL REFERENCES users(id) ON DELETE RESTRICT,
                              agent_id          BIGINT               REFERENCES users(id) ON DELETE SET NULL,
                              type              transaction_type     NOT NULL,
                              amount            NUMERIC(14,2)        NOT NULL CHECK (amount >= 0),
                              payment_status    payment_status_enum  NOT NULL DEFAULT 'PENDING',
                              transaction_date  DATE                 NOT NULL DEFAULT CURRENT_DATE,
                              created_at        TIMESTAMP            NOT NULL DEFAULT now()
);

CREATE INDEX idx_transactions_property
    ON transactions(property_id);

CREATE INDEX idx_transactions_buyer
    ON transactions(buyer_id);

CREATE INDEX idx_transactions_agent
    ON transactions(agent_id);

CREATE INDEX idx_transactions_date
    ON transactions(transaction_date);