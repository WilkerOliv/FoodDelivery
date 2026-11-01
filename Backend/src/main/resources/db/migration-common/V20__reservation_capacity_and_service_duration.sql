ALTER TABLE catalog_items ADD COLUMN appointment_duration_minutes INTEGER NOT NULL DEFAULT 30;
ALTER TABLE catalog_items ADD CONSTRAINT ck_catalog_service_duration CHECK (appointment_duration_minutes BETWEEN 5 AND 480);

ALTER TABLE reservations ADD COLUMN ends_at TIMESTAMP WITH TIME ZONE;
UPDATE reservations SET ends_at = starts_at + INTERVAL '30' MINUTE;
ALTER TABLE reservations ALTER COLUMN ends_at SET NOT NULL;
CREATE INDEX idx_reservations_store_interval ON reservations(store_id, starts_at, ends_at);

ALTER TABLE reservation_slots DROP CONSTRAINT uk_reservation_professional_slot;
ALTER TABLE reservation_slots ADD COLUMN slot_key UUID;
UPDATE reservation_slots SET slot_key = COALESCE(professional_id, '00000000-0000-0000-0000-000000000000');
ALTER TABLE reservation_slots ALTER COLUMN slot_key SET NOT NULL;
ALTER TABLE reservation_slots ADD COLUMN ends_at TIMESTAMP WITH TIME ZONE;
UPDATE reservation_slots SET ends_at = starts_at + INTERVAL '30' MINUTE;
ALTER TABLE reservation_slots ALTER COLUMN ends_at SET NOT NULL;
ALTER TABLE reservation_slots ADD CONSTRAINT uk_reservation_resource_start UNIQUE(store_id, slot_key, starts_at);
CREATE INDEX idx_reservation_slots_interval ON reservation_slots(store_id, starts_at, ends_at);
