-- Executar com psql administrativo em um projeto Supabase NOVO.
-- A senha vem de APP_DATABASE_PASSWORD no ambiente local, nunca deste arquivo.
-- Falha se a conta/schema já existirem; não altera permissões existentes silenciosamente.
\set ON_ERROR_STOP on
\getenv app_password APP_DATABASE_PASSWORD
\if :{?app_password}
SELECT length(:'app_password') >= 24 AS valid_password \gset
\if :valid_password
BEGIN;
CREATE ROLE fooddelivery_app LOGIN NOSUPERUSER NOCREATEDB NOCREATEROLE NOREPLICATION NOBYPASSRLS PASSWORD :'app_password';
-- O administrador gerenciado precisa poder assumir o novo papel para atribuir o schema.
GRANT fooddelivery_app TO CURRENT_USER;
CREATE SCHEMA fooddelivery AUTHORIZATION fooddelivery_app;
REVOKE ALL ON SCHEMA fooddelivery FROM PUBLIC;
ALTER ROLE fooddelivery_app SET search_path TO fooddelivery;
ALTER DEFAULT PRIVILEGES FOR ROLE fooddelivery_app IN SCHEMA fooddelivery REVOKE EXECUTE ON FUNCTIONS FROM PUBLIC;
COMMIT;
\else
\echo 'Use uma senha aleatória com pelo menos 24 caracteres.'
\quit 1
\endif
\else
\echo 'Defina APP_DATABASE_PASSWORD antes de executar; nenhuma alteração foi realizada.'
\quit 1
\endif
