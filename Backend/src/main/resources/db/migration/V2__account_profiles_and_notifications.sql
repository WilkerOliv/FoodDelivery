ALTER TABLE accounts ADD COLUMN address_complement VARCHAR(120);
ALTER TABLE accounts ADD COLUMN neighborhood VARCHAR(120);
ALTER TABLE accounts ADD COLUMN city VARCHAR(100);
ALTER TABLE accounts ADD COLUMN state VARCHAR(2);
ALTER TABLE accounts ADD COLUMN postal_code VARCHAR(9);

CREATE TABLE account_notifications (
    id UUID PRIMARY KEY,
    account_id UUID NOT NULL REFERENCES accounts(id) ON DELETE CASCADE,
    title VARCHAR(120) NOT NULL,
    message VARCHAR(500) NOT NULL,
    kind VARCHAR(16) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    read_at TIMESTAMP WITH TIME ZONE
);
CREATE INDEX idx_notifications_account_created ON account_notifications(account_id, created_at DESC);
