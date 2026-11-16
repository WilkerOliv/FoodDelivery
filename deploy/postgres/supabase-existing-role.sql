-- Executar como postgres, depois de criar fooddelivery_app com senha no painel.
-- Projeto novo: falha se o schema existir; não apaga ou substitui dados.
BEGIN;
DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_roles WHERE rolname = 'fooddelivery_app' AND rolcanlogin
        AND NOT rolsuper AND NOT rolcreatedb AND NOT rolcreaterole
        AND NOT rolreplication AND NOT rolbypassrls) THEN
        RAISE EXCEPTION 'fooddelivery_app precisa existir e permitir somente login';
    END IF;
END $$;
-- No Supabase, postgres não é superusuário e precisa poder assumir o papel
-- para atribuir a propriedade do schema e configurar seus privilégios padrão.
GRANT fooddelivery_app TO postgres;
CREATE SCHEMA fooddelivery AUTHORIZATION fooddelivery_app;
REVOKE ALL ON SCHEMA fooddelivery FROM PUBLIC, anon, authenticated;
ALTER ROLE fooddelivery_app SET search_path TO fooddelivery;
ALTER DEFAULT PRIVILEGES FOR ROLE fooddelivery_app IN SCHEMA fooddelivery
    REVOKE EXECUTE ON FUNCTIONS FROM PUBLIC;
COMMIT;
