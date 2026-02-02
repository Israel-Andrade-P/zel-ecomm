CREATE TABLE IF NOT EXISTS orders (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    public_id VARCHAR(50) NOT NULL UNIQUE,
    status VARCHAR(50) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_at TIMESTAMP,
    user_id BIGINT NOT NULL,
    location_id BIGINT NOT NULL,
    payment_id BIGINT,
    CONSTRAINT fk_orders_users FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_orders_locations FOREIGN KEY (location_id) REFERENCES locations(id),
    CONSTRAINT uq_orders_payment UNIQUE (payment_id),
    CONSTRAINT fk_orders_payments FOREIGN KEY (payment_id) REFERENCES payments(id)
);
