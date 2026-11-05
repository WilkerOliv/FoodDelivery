ALTER TABLE stores ADD COLUMN delivery_enabled BOOLEAN NOT NULL DEFAULT TRUE;
ALTER TABLE stores ADD COLUMN pickup_enabled BOOLEAN NOT NULL DEFAULT TRUE;
ALTER TABLE stores ADD COLUMN in_person_enabled BOOLEAN NOT NULL DEFAULT TRUE;
ALTER TABLE stores ADD CONSTRAINT ck_store_fulfillment CHECK (delivery_enabled OR pickup_enabled OR in_person_enabled);
