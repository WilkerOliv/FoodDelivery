ALTER TABLE accounts ADD COLUMN operator_application_status VARCHAR(16);
ALTER TABLE stores ADD COLUMN cover_image_url VARCHAR(255);

ALTER TABLE accounts ADD CONSTRAINT ck_operator_application_status
    CHECK (operator_application_status IS NULL OR operator_application_status IN ('PENDING', 'APPROVED', 'REJECTED'));

CREATE INDEX idx_accounts_operator_applications
    ON accounts(operator_application_status, created_at);
