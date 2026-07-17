CREATE EXTENSION IF NOT EXISTS pgcrypto;

ALTER TABLE carts
ADD COLUMN public_id UUID;

ALTER TABLE carts
ALTER COLUMN public_id
SET DEFAULT gen_random_uuid();

ALTER TABLE carts
ALTER COLUMN public_id SET NOT NULL;

CREATE UNIQUE INDEX uq_carts_public_id
ON carts(public_id);



