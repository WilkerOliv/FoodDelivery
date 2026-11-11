-- Runs only when PostgreSQL initializes an EMPTY data volume.
-- Keep the bootstrap administrator separate from the application owner.
\getenv app_password DATABASE_PASSWORD
CREATE ROLE fooddelivery_app LOGIN PASSWORD :'app_password'
  NOSUPERUSER NOCREATEDB NOCREATEROLE NOREPLICATION NOBYPASSRLS;
CREATE DATABASE fooddelivery OWNER fooddelivery_app;
\connect fooddelivery
REVOKE CREATE ON SCHEMA public FROM PUBLIC;
GRANT ALL ON SCHEMA public TO fooddelivery_app;
