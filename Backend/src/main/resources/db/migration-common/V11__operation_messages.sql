ALTER TABLE customer_orders ADD COLUMN customer_note VARCHAR(500);
ALTER TABLE reservations ADD COLUMN customer_note VARCHAR(500);

CREATE TABLE operation_messages (
    id UUID PRIMARY KEY,
    order_id UUID REFERENCES customer_orders(id) ON DELETE CASCADE,
    reservation_id UUID REFERENCES reservations(id) ON DELETE CASCADE,
    delivery_task_id UUID REFERENCES delivery_tasks(id) ON DELETE CASCADE,
    sender_id UUID NOT NULL REFERENCES accounts(id),
    body VARCHAR(1000) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT ck_operation_message_single_parent CHECK (
        (CASE WHEN order_id IS NULL THEN 0 ELSE 1 END) +
        (CASE WHEN reservation_id IS NULL THEN 0 ELSE 1 END) +
        (CASE WHEN delivery_task_id IS NULL THEN 0 ELSE 1 END) = 1
    ),
    CONSTRAINT ck_operation_message_body CHECK (length(trim(body)) > 0)
);
CREATE INDEX idx_operation_message_order ON operation_messages(order_id, created_at);
CREATE INDEX idx_operation_message_reservation ON operation_messages(reservation_id, created_at);
CREATE INDEX idx_operation_message_delivery ON operation_messages(delivery_task_id, created_at);
