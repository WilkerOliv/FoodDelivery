ALTER TABLE stores ADD COLUMN cnpj VARCHAR(14);
CREATE UNIQUE INDEX ux_stores_cnpj ON stores(cnpj);

CREATE TABLE service_cities (
    id UUID PRIMARY KEY,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(2) NOT NULL,
    postal_code VARCHAR(9),
    enabled BOOLEAN NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    UNIQUE(city, state)
);

CREATE TABLE platform_requests (
    id UUID PRIMARY KEY,
    account_id UUID NOT NULL REFERENCES accounts(id),
    request_type VARCHAR(16) NOT NULL,
    status VARCHAR(16) NOT NULL,
    requested_role VARCHAR(20),
    city VARCHAR(100),
    state VARCHAR(2),
    postal_code VARCHAR(9),
    store_id UUID REFERENCES stores(id),
    review_note VARCHAR(500),
    reviewed_by UUID REFERENCES accounts(id),
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    reviewed_at TIMESTAMP WITH TIME ZONE
);
CREATE INDEX idx_platform_requests_account_created ON platform_requests(account_id, created_at);
CREATE INDEX idx_platform_requests_status_created ON platform_requests(status, created_at);
