CREATE TABLE IF NOT EXISTS payments (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    payment_method VARCHAR(255) NOT NULL,
    pg_id VARCHAR(255) NOT NULL,
    pg_status VARCHAR(255) NOT NULL,
    pg_response_message VARCHAR(255) NOT NULL,
    pg_name VARCHAR(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_at TIMESTAMP
);

