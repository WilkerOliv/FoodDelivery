ALTER TABLE accounts ADD COLUMN cpf VARCHAR(11);
ALTER TABLE accounts ADD COLUMN granted_roles VARCHAR(80);
ALTER TABLE accounts ADD COLUMN suspended_roles VARCHAR(80) NOT NULL DEFAULT '';
ALTER TABLE accounts ADD COLUMN requested_operator_role VARCHAR(20);
ALTER TABLE accounts ADD COLUMN password_change_required BOOLEAN NOT NULL DEFAULT FALSE;

UPDATE accounts SET granted_roles = role WHERE granted_roles IS NULL;
ALTER TABLE accounts ALTER COLUMN granted_roles SET NOT NULL;
ALTER TABLE accounts ALTER COLUMN granted_roles SET DEFAULT 'CUSTOMER';

CREATE UNIQUE INDEX uk_accounts_cpf ON accounts(cpf) WHERE cpf IS NOT NULL;
ALTER TABLE accounts ADD CONSTRAINT ck_requested_operator_role
    CHECK (requested_operator_role IS NULL OR requested_operator_role IN ('MERCHANT', 'COURIER'));

ALTER TABLE stores ADD COLUMN city VARCHAR(100);
ALTER TABLE stores ADD COLUMN state VARCHAR(2);
CREATE INDEX idx_stores_city_state_public ON stores(city, state, active, approved);
