CREATE TYPE inquiry_status AS ENUM (
    'NEW',
    'RESPONDED',
    'CLOSED'
);

CREATE TABLE inquiries (
                           id            BIGSERIAL PRIMARY KEY,
                           property_id   BIGINT         NOT NULL REFERENCES properties(id) ON DELETE CASCADE,
                           customer_id   BIGINT         NOT NULL REFERENCES users(id) ON DELETE CASCADE,
                           agent_id      BIGINT                  REFERENCES users(id) ON DELETE SET NULL,
                           message       TEXT           NOT NULL,
                           status        inquiry_status NOT NULL DEFAULT 'NEW',
                           created_at    TIMESTAMP      NOT NULL DEFAULT now(),
                           updated_at    TIMESTAMP      NOT NULL DEFAULT now()
);

CREATE INDEX idx_inquiries_property ON inquiries(property_id);
CREATE INDEX idx_inquiries_customer ON inquiries(customer_id);
CREATE INDEX idx_inquiries_agent    ON inquiries(agent_id);
CREATE INDEX idx_inquiries_status   ON inquiries(status);