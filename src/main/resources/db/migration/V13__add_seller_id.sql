ALTER TABLE products
ADD COLUMN seller_id BIGINT NOT NULL,
ADD CONSTRAINT fk_products_seller FOREIGN KEY (seller_id) REFERENCES users(id);
