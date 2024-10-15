CREATE USER 'theraven'@'localhost' IDENTIFIED BY 'theraven';
CREATE DATABASE theraven_db;
GRANT ALL PRIVILEGES ON theraven_db.* TO 'theraven'@'localhost';
FLUSH PRIVILEGES ;
