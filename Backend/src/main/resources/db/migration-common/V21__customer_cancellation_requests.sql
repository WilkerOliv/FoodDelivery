ALTER TABLE customer_orders ADD COLUMN cancellation_requested_at TIMESTAMP WITH TIME ZONE;
ALTER TABLE customer_orders ADD COLUMN cancellation_request_status VARCHAR(16) NOT NULL DEFAULT 'NONE';
ALTER TABLE customer_orders ADD COLUMN cancellation_request_note VARCHAR(500);
ALTER TABLE reservations ADD COLUMN cancellation_requested_at TIMESTAMP WITH TIME ZONE;
ALTER TABLE reservations ADD COLUMN cancellation_request_status VARCHAR(16) NOT NULL DEFAULT 'NONE';
ALTER TABLE reservations ADD COLUMN cancellation_request_note VARCHAR(500);
CREATE INDEX idx_orders_cancellation_request ON customer_orders(cancellation_requested_at);
CREATE INDEX idx_reservations_cancellation_request ON reservations(cancellation_requested_at);
