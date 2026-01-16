-- 1. Rename table
ALTER TABLE addresses RENAME TO locations;

-- 2. Rename foreign key constraint
ALTER TABLE locations
RENAME CONSTRAINT fk_addresses_user TO fk_locations_user;

-- 3. Rename index
ALTER INDEX idx_addresses_user_id RENAME TO idx_locations_user_id;
