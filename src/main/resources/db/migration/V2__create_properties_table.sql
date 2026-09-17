CREATE TYPE property_type AS ENUM (
    'HOUSE',
    'APARTMENT',
    'LAND',
    'COMMERCIAL'
);

CREATE TYPE property_status AS ENUM (
    'AVAILABLE',
    'PENDING',
    'SOLD',
    'RENTED'
);

CREATE TABLE properties (
                            id            BIGSERIAL PRIMARY KEY,
                            title         VARCHAR(200)     NOT NULL,
                            description   TEXT,
                            type          property_type    NOT NULL,
                            price         NUMERIC(14,2)    NOT NULL CHECK (price >= 0),
                            size          NUMERIC(10,2),
                            location      VARCHAR(200)     NOT NULL,
                            status        property_status  NOT NULL DEFAULT 'AVAILABLE',
                            owner_id      BIGINT           NOT NULL REFERENCES users(id) ON DELETE RESTRICT,
                            agent_id      BIGINT           REFERENCES users(id) ON DELETE SET NULL,
                            image_urls    JSONB            DEFAULT '[]',
                            created_at    TIMESTAMP        NOT NULL DEFAULT now(),
                            updated_at    TIMESTAMP        NOT NULL DEFAULT now()
);

CREATE INDEX idx_properties_location ON properties(location);
CREATE INDEX idx_properties_type     ON properties(type);
CREATE INDEX idx_properties_status   ON properties(status);
CREATE INDEX idx_properties_price    ON properties(price);
CREATE INDEX idx_properties_owner    ON properties(owner_id);
CREATE INDEX idx_properties_agent    ON properties(agent_id);