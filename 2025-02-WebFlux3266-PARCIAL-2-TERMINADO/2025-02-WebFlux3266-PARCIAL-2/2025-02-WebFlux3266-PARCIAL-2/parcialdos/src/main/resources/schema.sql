-- Recreate table each startup: drop then create
DROP TABLE IF EXISTS accounts;

CREATE TABLE accounts (
  id BIGINT NOT NULL AUTO_INCREMENT,
  account_number VARCHAR(50) NOT NULL,
  owner_name VARCHAR(150) NOT NULL,
  balance DECIMAL(19,2) NOT NULL DEFAULT 0.00,
  active BOOLEAN NOT NULL DEFAULT TRUE,
  PRIMARY KEY (id),
  UNIQUE KEY uk_account_number (account_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
