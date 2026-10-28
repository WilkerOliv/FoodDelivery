ALTER TABLE accounts ADD COLUMN phone_verified BOOLEAN NOT NULL DEFAULT FALSE;

CREATE TABLE recovery_codes (
    id UUID PRIMARY KEY,
    account_id UUID NOT NULL REFERENCES accounts(id) ON DELETE CASCADE,
    purpose VARCHAR(24) NOT NULL,
    channel VARCHAR(16) NOT NULL,
    code_hash VARCHAR(100) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
    next_allowed_at TIMESTAMP WITH TIME ZONE NOT NULL,
    attempts INTEGER NOT NULL DEFAULT 0,
    consumed_at TIMESTAMP WITH TIME ZONE
);
CREATE INDEX idx_recovery_account_purpose ON recovery_codes(account_id, purpose, created_at DESC);
