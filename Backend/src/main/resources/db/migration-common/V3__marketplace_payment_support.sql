ALTER TABLE payments ALTER COLUMN order_id DROP NOT NULL;
ALTER TABLE payments ADD COLUMN reservation_id UUID UNIQUE REFERENCES reservations(id);
ALTER TABLE payments ADD COLUMN provider_preference_id VARCHAR(120);
ALTER TABLE payments ADD COLUMN checkout_url VARCHAR(1000);
ALTER TABLE stores ADD COLUMN marketplace_fee_bps INTEGER NOT NULL DEFAULT 0 CHECK (marketplace_fee_bps BETWEEN 0 AND 5000);
ALTER TABLE stores ADD COLUMN delivery_fee_cents BIGINT NOT NULL DEFAULT 0 CHECK (delivery_fee_cents BETWEEN 0 AND 100000);
ALTER TABLE customer_orders ADD COLUMN subtotal_cents BIGINT NOT NULL DEFAULT 0;
UPDATE customer_orders SET subtotal_cents=total_cents;
ALTER TABLE customer_orders ALTER COLUMN subtotal_cents DROP DEFAULT;
ALTER TABLE customer_orders ADD COLUMN delivery_fee_cents BIGINT NOT NULL DEFAULT 0 CHECK (delivery_fee_cents >= 0);
ALTER TABLE payments ADD CONSTRAINT ck_payment_target CHECK (
    (order_id IS NOT NULL AND reservation_id IS NULL) OR
    (order_id IS NULL AND reservation_id IS NOT NULL)
);

CREATE TABLE marketplace_payment_accounts (
    id UUID PRIMARY KEY,
    store_id UUID NOT NULL UNIQUE REFERENCES stores(id) ON DELETE CASCADE,
    mp_user_id VARCHAR(80),
    access_token_encrypted VARCHAR(2048),
    refresh_token_encrypted VARCHAR(2048),
    token_expires_at TIMESTAMP WITH TIME ZONE,
    oauth_state_hash VARCHAR(128),
    oauth_state_expires_at TIMESTAMP WITH TIME ZONE,
    oauth_code_verifier_encrypted VARCHAR(256),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);
CREATE INDEX idx_marketplace_oauth_state ON marketplace_payment_accounts(oauth_state_hash);
