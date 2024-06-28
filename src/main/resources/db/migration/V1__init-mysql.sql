CREATE TABLE beer (
                      price DECIMAL(38,2) NOT NULL,
                      quantity_on_hand INT NOT NULL,
                      version INT,
                      created_date DATETIME NOT NULL,
                      update_date DATETIME,
                      id VARCHAR(255) NOT NULL,
                      beer_name VARCHAR(55) NOT NULL,
                      upc VARCHAR(255) NOT NULL,
                      beer_style ENUM('ALE', 'LAGER', 'PILSNER', 'PORTER', 'STOUT'),
                      PRIMARY KEY (id)
) ENGINE=InnoDB;
