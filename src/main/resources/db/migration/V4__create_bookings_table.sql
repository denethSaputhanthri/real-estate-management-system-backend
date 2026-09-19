CREATE TYPE booking_status AS ENUM (
    'SCHEDULED',
    'COMPLETED',
    'CANCELLED'
);

CREATE TABLE bookings (
                          id            BIGSERIAL PRIMARY KEY,
                          inquiry_id    BIGINT          NOT NULL REFERENCES inquiries(id) ON DELETE CASCADE,
                          visit_date    TIMESTAMP       NOT NULL,
                          status        booking_status  NOT NULL DEFAULT 'SCHEDULED',
                          created_at    TIMESTAMP       NOT NULL DEFAULT now(),
                          updated_at    TIMESTAMP       NOT NULL DEFAULT now(),
                          UNIQUE (inquiry_id)
);

CREATE INDEX idx_bookings_visit_date ON bookings(visit_date);
CREATE INDEX idx_bookings_status     ON bookings(status);