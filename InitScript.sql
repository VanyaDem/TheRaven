CREATE USER 'theraven'@'%' IDENTIFIED BY 'theraven';
CREATE DATABASE theraven_db;
GRANT ALL PRIVILEGES ON *.* TO 'theraven'@'%';
FLUSH PRIVILEGES ;
