ALTER TABLE reservations DROP CONSTRAINT uk_reservation_slot;

CREATE TABLE reservation_slots (
    id UUID PRIMARY KEY,
    store_id UUID NOT NULL REFERENCES stores(id) ON DELETE CASCADE,
    reservation_id UUID NOT NULL UNIQUE REFERENCES reservations(id) ON DELETE CASCADE,
    starts_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT uk_reservation_slot_active UNIQUE(store_id, starts_at)
);
INSERT INTO reservation_slots(id,store_id,reservation_id,starts_at)
SELECT id,store_id,id,starts_at FROM reservations
WHERE status IN ('PENDING_PAYMENT','CONFIRMED','IN_SERVICE');
CREATE INDEX idx_reservation_slots_start ON reservation_slots(starts_at);
