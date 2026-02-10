ALTER TABLE carts
DROP CONSTRAINT fk_carts_users,
ADD CONSTRAINT fk_carts_users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE locations
DROP CONSTRAINT fk_locations_user,
ADD CONSTRAINT fk_locations_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE orders
DROP CONSTRAINT fk_orders_users,
ADD CONSTRAINT fk_orders_users FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE products
DROP CONSTRAINT fk_products_seller,
ADD CONSTRAINT fk_products_seller FOREIGN KEY (seller_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE user_role
DROP CONSTRAINT fk_user_role_user,
ADD CONSTRAINT fk_user_role_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;
