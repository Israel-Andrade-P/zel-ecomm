CREATE TABLE IF NOT EXISTS cart_items (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    price NUMERIC(19,4) NOT NULL CHECK (price >= 0),
    quantity INTEGER NOT NULL CHECK (quantity > 0),
    discount INTEGER CHECK (discount BETWEEN 0 AND 100),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    last_modified_at TIMESTAMP,
    cart_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    CONSTRAINT fk_item_cart FOREIGN KEY (cart_id) REFERENCES carts(id),
    CONSTRAINT fk_item_product FOREIGN KEY (product_id) REFERENCES products(id)
);
