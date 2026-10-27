ALTER TABLE payments ADD COLUMN refund_idempotency_key UUID;
ALTER TABLE payments ADD COLUMN refund_provider_reference VARCHAR(120);
ALTER TABLE payments ADD COLUMN refund_attempts INTEGER NOT NULL DEFAULT 0 CHECK (refund_attempts >= 0);
ALTER TABLE payments ADD COLUMN last_refund_attempt_at TIMESTAMP WITH TIME ZONE;
ALTER TABLE payments ADD COLUMN refund_last_error VARCHAR(300);
