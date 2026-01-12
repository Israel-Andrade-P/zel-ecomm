INSERT INTO roles (role, created_at, last_modified_at)
VALUES
  ('USER', now(), now()),
  ('SELLER', now(), now()),
  ('ADMIN', now(), now())
ON CONFLICT (role) DO NOTHING;
