CREATE USER theraven WITH PASSWORD 'theraven';
CREATE SCHEMA theraven_public;
GRANT ALL PRIVILEGES ON DATABASE postgres TO theraven;
GRANT USAGE ON SCHEMA theraven_public TO theraven;
GRANT ALL PRIVILEGES ON SCHEMA theraven_public TO theraven;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA theraven_public TO theraven;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA theraven_public TO theraven;
