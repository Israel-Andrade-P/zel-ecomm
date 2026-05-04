ALTER TABLE order_items
DROP CONSTRAINT fk_item_order,
ADD CONSTRAINT fk_item_order FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE;