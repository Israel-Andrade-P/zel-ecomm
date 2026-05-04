-- Rename the identity sequence only
ALTER SEQUENCE IF EXISTS addresses_id_seq
RENAME TO locations_id_seq;
