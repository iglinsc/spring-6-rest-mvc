CREATE TABLE customer (
                          id CHAR(36) PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          email VARCHAR(255),
                          version BIGINT DEFAULT 0,
                          created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          update_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB;