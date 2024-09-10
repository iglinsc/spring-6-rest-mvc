CREATE TABLE beer_order (
                            id VARCHAR(36) NOT NULL,
                            created_date DATETIME(6),
                            customer_ref VARCHAR(255),
                            last_modified_date DATETIME(6),
                            version BIGINT,
                            customer_id VARCHAR(36),
                            PRIMARY KEY (id),
                            CONSTRAINT fk_customer
                                FOREIGN KEY (customer_id)
                                    REFERENCES customer (id)
) ENGINE=InnoDB;
