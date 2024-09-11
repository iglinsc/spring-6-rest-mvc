CREATE TABLE customer (
                          id CHAR(36) PRIMARY KEY,                        -- UUID stored as CHAR(36)
                          name VARCHAR(255) NOT NULL,                     -- Assuming name is mandatory
                          email VARCHAR(255) UNIQUE,                      -- Email should be unique
                          version INT DEFAULT 0,                          -- Version column
                          createdDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- Automatically set to current timestamp
                          updateDate TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- Automatically updated on row modification
);
