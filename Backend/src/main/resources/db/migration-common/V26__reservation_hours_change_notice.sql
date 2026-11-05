ALTER TABLE reservations ADD COLUMN operating_hours_override BOOLEAN NOT NULL DEFAULT FALSE;
ALTER TABLE reservations ADD COLUMN operating_hours_notice VARCHAR(500);
ALTER TABLE reservations ADD COLUMN operating_hours_changed_at TIMESTAMP WITH TIME ZONE;
CREATE INDEX idx_reservation_store_hours ON reservations(store_id, status, ends_at);
