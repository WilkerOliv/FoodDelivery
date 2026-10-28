CREATE TABLE customer_addresses (
    id UUID PRIMARY KEY,
    account_id UUID NOT NULL REFERENCES accounts(id) ON DELETE CASCADE,
    label VARCHAR(40) NOT NULL,
    address_line VARCHAR(250) NOT NULL,
    address_complement VARCHAR(120),
    neighborhood VARCHAR(120) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(2) NOT NULL,
    postal_code VARCHAR(9) NOT NULL,
    is_default BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE INDEX idx_customer_address_owner ON customer_addresses(account_id);
