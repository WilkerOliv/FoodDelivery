-- Executar uma vez como administrador, após aplicar as migrações ao schema privado.
-- A senha de login será definida separadamente com \password.
BEGIN;
CREATE ROLE fooddelivery_runtime LOGIN NOSUPERUSER NOCREATEDB NOCREATEROLE NOREPLICATION NOBYPASSRLS;
ALTER ROLE fooddelivery_runtime SET search_path TO fooddelivery;
SET LOCAL ROLE fooddelivery_app;
GRANT USAGE ON SCHEMA fooddelivery TO fooddelivery_runtime;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA fooddelivery TO fooddelivery_runtime;
REVOKE ALL ON fooddelivery.flyway_schema_history FROM fooddelivery_runtime;
REVOKE UPDATE, DELETE ON fooddelivery.admin_audit_events FROM fooddelivery_runtime;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA fooddelivery TO fooddelivery_runtime;
ALTER DEFAULT PRIVILEGES IN SCHEMA fooddelivery
    GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO fooddelivery_runtime;
ALTER DEFAULT PRIVILEGES IN SCHEMA fooddelivery
    GRANT USAGE, SELECT ON SEQUENCES TO fooddelivery_runtime;
COMMIT;
