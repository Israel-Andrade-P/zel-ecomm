CREATE EXTENSION IF NOT EXISTS pgcrypto;

ALTER TABLE locations
ADD COLUMN public_id UUID;

UPDATE locations
SET public_id = gen_random_uuid()
WHERE public_id IS NULL;

ALTER TABLE locations
ALTER COLUMN public_id SET NOT NULL;

CREATE UNIQUE INDEX uq_locations_public_id
ON locations(public_id);

ALTER TABLE locations
ALTER COLUMN public_id
SET DEFAULT gen_random_uuid();


