CREATE TABLE store_professionals (
    id UUID PRIMARY KEY,
    store_id UUID NOT NULL REFERENCES stores(id) ON DELETE CASCADE,
    name VARCHAR(100) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    CONSTRAINT uk_store_professional_name UNIQUE (store_id, name)
);

ALTER TABLE reservations ADD COLUMN professional_id UUID REFERENCES store_professionals(id) ON DELETE SET NULL;
ALTER TABLE reservation_slots ADD COLUMN professional_id UUID REFERENCES store_professionals(id) ON DELETE SET NULL;
ALTER TABLE reservation_slots DROP CONSTRAINT uk_reservation_slot_active;
ALTER TABLE reservation_slots ADD CONSTRAINT uk_reservation_professional_slot UNIQUE (store_id, professional_id, starts_at);
CREATE INDEX idx_store_professionals_store ON store_professionals(store_id, active);
