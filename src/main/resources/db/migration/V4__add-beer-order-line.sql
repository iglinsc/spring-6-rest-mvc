
CREATE TABLE beer_order_line (
                                 id CHAR(36) PRIMARY KEY,
                                 beer_id CHAR(36) NOT NULL,
                                 created_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 last_modified_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                 order_quantity INT DEFAULT 0,
                                 quantity_allocated INT DEFAULT 0,
                                 version BIGINT,
                                 beer_order_id CHAR(36) NOT NULL,
                                 CONSTRAINT fk_beer FOREIGN KEY (beer_id) REFERENCES beer(id),
                                 CONSTRAINT fk_beer_order FOREIGN KEY (beer_order_id) REFERENCES beer_order(id)
) ENGINE=InnoDB;