-- V1__create_beer_table.sql

CREATE TABLE beer (
                      id VARCHAR(36) NOT NULL,
                      version INT,
                      beer_name VARCHAR(55) NOT NULL,
                      beer_style ENUM('ALE', 'LAGER', 'PILSNER', 'PORTER', 'STOUT'),
                      upc VARCHAR(255) NOT NULL,
                      email VARCHAR(255),
                      quantity_on_hand INT,
                      price DECIMAL(38,2) NOT NULL,
                      created_date DATETIME(6) NOT NULL,
                      update_date DATETIME(6),
                      PRIMARY KEY (id)
) ENGINE=InnoDB;


CREATE TABLE beer_order (
                            id VARCHAR(36) NOT NULL,
                            created_date DATETIME(6),
                            customer_ref VARCHAR(255),
                            last_modified_date DATETIME(6),
                            version BIGINT,
                            customer_id VARCHAR(36),
                            PRIMARY KEY (id)
) ENGINE=InnoDB;


create table beer (price decimal(38,2) not null, quantity_on_hand integer, version integer, created_date datetime(6), update_date datetime(6), id varchar not null, beer_name varchar(55) not null, email varchar(255), upc varchar(255) not null, beer_style enum ('ALE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT'), primary key (id)) engine=InnoDB;
create table customer (version integer, created_date datetime(6), update_date datetime(6), id varchar(36) not null, email varchar(255), name varchar(255), primary key (id)) engine=InnoDB;
create table beer (price decimal(38,2) not null, quantity_on_hand integer, version integer, created_date datetime(6), update_date datetime(6), id varchar not null, beer_name varchar(55) not null, email varchar(255), upc varchar(255) not null, beer_style enum ('ALE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT'), primary key (id)) engine=InnoDB;
create table customer (version integer, created_date datetime(6), update_date datetime(6), id varchar(36) not null, email varchar(255), name varchar(255), primary key (id)) engine=InnoDB;
create table beer (price decimal(38,2) not null, quantity_on_hand integer, version integer, created_date datetime(6), update_date datetime(6), id varchar not null, beer_name varchar(55) not null, email varchar(255), upc varchar(255) not null, beer_style enum ('ALE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT'), primary key (id)) engine=InnoDB;
create table customer (version integer, created_date datetime(6), update_date datetime(6), id varchar(36) not null, email varchar(255), name varchar(255), primary key (id)) engine=InnoDB;
create table beer (price decimal(38,2) not null, quantity_on_hand integer, version integer, created_date datetime(6), update_date datetime(6), id varchar not null, beer_name varchar(55) not null, email varchar(255), upc varchar(255) not null, beer_style enum ('ALE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT'), primary key (id)) engine=InnoDB;
create table customer (version integer, created_date datetime(6), update_date datetime(6), id varchar(36) not null, email varchar(255), name varchar(255), primary key (id)) engine=InnoDB;
create table beer (price decimal(38,2) not null, quantity_on_hand integer, version integer, created_date datetime(6), update_date datetime(6), id varchar not null, beer_name varchar(55) not null, email varchar(255), upc varchar(255) not null, beer_style enum ('ALE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT'), primary key (id)) engine=InnoDB;
create table customer (version integer, created_date datetime(6), update_date datetime(6), id varchar(36) not null, email varchar(255), name varchar(255), primary key (id)) engine=InnoDB;
create table beer (price decimal(38,2) not null, quantity_on_hand integer, version integer, created_date datetime(6), update_date datetime(6), id varchar not null, beer_name varchar(55) not null, email varchar(255), upc varchar(255) not null, beer_style enum ('ALE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT'), primary key (id)) engine=InnoDB;
create table customer (version integer, created_date datetime(6), update_date datetime(6), id varchar(36) not null, email varchar(255), name varchar(255), primary key (id)) engine=InnoDB;
create table beer (price decimal(38,2) not null, quantity_on_hand integer, version integer, created_date datetime(6), update_date datetime(6), id varchar not null, beer_name varchar(55) not null, email varchar(255), upc varchar(255) not null, beer_style enum ('ALE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT'), primary key (id)) engine=InnoDB;
create table customer (version integer, created_date datetime(6), update_date datetime(6), id varchar(36) not null, email varchar(255), name varchar(255), primary key (id)) engine=InnoDB;
create table beer (price decimal(38,2) not null, quantity_on_hand integer, version integer, created_date datetime(6), update_date datetime(6), id varchar not null, beer_name varchar(55) not null, email varchar(255), upc varchar(255) not null, beer_style enum ('ALE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT'), primary key (id)) engine=InnoDB;
create table customer (version integer, created_date datetime(6), update_date datetime(6), id varchar(36) not null, email varchar(255), name varchar(255), primary key (id)) engine=InnoDB;
create table beer (price decimal(38,2) not null, quantity_on_hand integer, version integer, created_date datetime(6), update_date datetime(6), id varchar not null, beer_name varchar(55) not null, email varchar(255), upc varchar(255) not null, beer_style enum ('ALE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT'), primary key (id)) engine=InnoDB;
create table customer (version integer, created_date datetime(6), update_date datetime(6), id varchar(36) not null, email varchar(255), name varchar(255), primary key (id)) engine=InnoDB;
create table beer (price decimal(38,2) not null, quantity_on_hand integer, version integer, created_date datetime(6), update_date datetime(6), id varchar not null, beer_name varchar(55) not null, email varchar(255), upc varchar(255) not null, beer_style enum ('ALE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT'), primary key (id)) engine=InnoDB;
create table customer (version integer, created_date datetime(6), update_date datetime(6), id varchar(36) not null, email varchar(255), name varchar(255), primary key (id)) engine=InnoDB;
create table beer (price decimal(38,2) not null, quantity_on_hand integer, version integer, created_date datetime(6), update_date datetime(6), id varchar not null, beer_name varchar(55) not null, email varchar(255), upc varchar(255) not null, beer_style enum ('ALE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT'), primary key (id)) engine=InnoDB;
create table customer (version integer, created_date datetime(6), update_date datetime(6), id varchar(36) not null, email varchar(255), name varchar(255), primary key (id)) engine=InnoDB;
create table beer (price decimal(38,2) not null, quantity_on_hand integer, version integer, created_date datetime(6), update_date datetime(6), id varchar not null, beer_name varchar(55) not null, email varchar(255), upc varchar(255) not null, beer_style enum ('ALE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT'), primary key (id)) engine=InnoDB;
create table customer (version integer, created_date datetime(6), update_date datetime(6), id varchar(36) not null, email varchar(255), name varchar(255), primary key (id)) engine=InnoDB;
create table beer (price decimal(38,2) not null, quantity_on_hand integer, version integer, created_date datetime(6), update_date datetime(6), id varchar not null, beer_name varchar(55) not null, email varchar(255), upc varchar(255) not null, beer_style enum ('ALE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT'), primary key (id)) engine=InnoDB;
create table customer (version integer, created_date datetime(6), update_date datetime(6), id varchar(36) not null, email varchar(255), name varchar(255), primary key (id)) engine=InnoDB;
create table beer (price decimal(38,2) not null, quantity_on_hand integer, version integer, created_date datetime(6), update_date datetime(6), id varchar not null, beer_name varchar(55) not null, email varchar(255), upc varchar(255) not null, beer_style enum ('ALE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT'), primary key (id)) engine=InnoDB;
create table customer (version integer, created_date datetime(6), update_date datetime(6), id varchar(36) not null, email varchar(255), name varchar(255), primary key (id)) engine=InnoDB;
create table beer (price decimal(38,2) not null, quantity_on_hand integer, version integer, created_date datetime(6), update_date datetime(6), id varchar not null, beer_name varchar(55) not null, email varchar(255), upc varchar(255) not null, beer_style enum ('ALE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT'), primary key (id)) engine=InnoDB;
create table customer (version integer, created_date datetime(6), update_date datetime(6), id varchar(36) not null, email varchar(255), name varchar(255), primary key (id)) engine=InnoDB;
create table beer (price decimal(38,2) not null, quantity_on_hand integer, version integer, created_date datetime(6), update_date datetime(6), id varchar not null, beer_name varchar(55) not null, email varchar(255), upc varchar(255) not null, beer_style enum ('ALE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT'), primary key (id)) engine=InnoDB;
create table customer (version integer, created_date datetime(6), update_date datetime(6), id varchar(36) not null, email varchar(255), name varchar(255), primary key (id)) engine=InnoDB;
create table beer (price decimal(38,2) not null, quantity_on_hand integer, version integer, created_date datetime(6), update_date datetime(6), id varchar not null, beer_name varchar(55) not null, email varchar(255), upc varchar(255) not null, beer_style enum ('ALE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT'), primary key (id)) engine=InnoDB;
create table customer (version integer, created_date datetime(6), update_date datetime(6), id varchar(36) not null, email varchar(255), name varchar(255), primary key (id)) engine=InnoDB;
create table beer (price decimal(38,2) not null, quantity_on_hand integer, version integer, created_date datetime(6), update_date datetime(6), id varchar not null, beer_name varchar(55) not null, email varchar(255), upc varchar(255) not null, beer_style enum ('ALE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT'), primary key (id)) engine=InnoDB;
create table customer (version integer, created_date datetime(6), update_date datetime(6), id varchar(36) not null, email varchar(255), name varchar(255), primary key (id)) engine=InnoDB;
create table beer (price decimal(38,2) not null, quantity_on_hand integer, version integer, created_date datetime(6), update_date datetime(6), id varchar not null, beer_name varchar(55) not null, email varchar(255), upc varchar(255) not null, beer_style enum ('ALE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT'), primary key (id)) engine=InnoDB;
create table customer (version integer, created_date datetime(6), update_date datetime(6), id varchar(36) not null, email varchar(255), name varchar(255), primary key (id)) engine=InnoDB;
create table beer (price decimal(38,2) not null, quantity_on_hand integer, version integer, created_date datetime(6), update_date datetime(6), id varchar not null, beer_name varchar(55) not null, email varchar(255), upc varchar(255) not null, beer_style enum ('ALE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT'), primary key (id)) engine=InnoDB;
create table beer_order (created_date datetime(6) not null, last_modified_date datetime(6), version bigint, id varchar(36) not null, customer_ref varchar(255), primary key (id)) engine=InnoDB;
create table beer_order_line (order_quantity integer not null, quantity_allocated integer, created_date datetime(6), last_modified_date datetime(6), version bigint, id varchar(36) not null, primary key (id)) engine=InnoDB;
create table customer (version integer, created_date datetime(6), update_date datetime(6), id varchar(36) not null, email varchar(255), name varchar(255), primary key (id)) engine=InnoDB;
create table beer (price decimal(38,2) not null, quantity_on_hand integer, version integer, created_date datetime(6), update_date datetime(6), id varchar not null, beer_name varchar(55) not null, email varchar(255), upc varchar(255) not null, beer_style enum ('ALE','IPA','LAGER','PALE_ALE','PILSNER','PORTER','SAISON','STOUT','WHEAT'), primary key (id)) engine=InnoDB;
create table beer_order (created_date datetime(6) not null, last_modified_date datetime(6), version bigint, customer_id varchar(36), id varchar(36) not null, customer_ref varchar(255), primary key (id)) engine=InnoDB;
create table beer_order_line (order_quantity integer not null, quantity_allocated integer, created_date datetime(6), last_modified_date datetime(6), version bigint, id varchar(36) not null, primary key (id)) engine=InnoDB;
create table customer (version integer, created_date datetime(6), update_date datetime(6), id varchar(36) not null, email varchar(255), name varchar(255), primary key (id)) engine=InnoDB;
alter table beer_order add constraint FK5siih2e7vpx70nx4wexpxpji foreign key (customer_id) references customer (id);
