CREATE ROLE fooddelivery_app LOGIN PASSWORD :'app_password';
CREATE DATABASE fooddelivery OWNER fooddelivery_app;
\connect fooddelivery
GRANT ALL ON SCHEMA public TO fooddelivery_app;
