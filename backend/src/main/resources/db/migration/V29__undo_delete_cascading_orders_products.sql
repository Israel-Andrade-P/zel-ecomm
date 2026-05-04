ALTER TABLE orders
DROP CONSTRAINT fk_orders_users,
ADD CONSTRAINT fk_orders_users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE RESTRICT;

ALTER TABLE products
DROP CONSTRAINT fk_products_seller,
ADD CONSTRAINT fk_products_seller FOREIGN KEY (seller_id) REFERENCES users(id) ON DELETE RESTRICT;