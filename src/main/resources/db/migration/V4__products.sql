CREATE TABLE IF NOT EXISTS products (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(200),
    quantity BIGINT DEFAULT 0 CHECK (quantity >= 0),
    price NUMERIC(10,2),
    special_price NUMERIC(10,2),
    category_id BIGINT NOT NULL,
    CONSTRAINT fk_products_categories FOREIGN KEY (category_id) REFERENCES categories (id) ON DELETE RESTRICT
);

CREATE INDEX idx_products_category_id ON products (category_id);