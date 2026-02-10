ALTER TABLE cart_items
DROP CONSTRAINT fk_item_cart,
ADD CONSTRAINT fk_item_cart FOREIGN KEY (cart_id) REFERENCES carts(id) ON DELETE CASCADE;