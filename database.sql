CREATE DATABASE inventory_service;

USE inventory_service;

CREATE TABLE inventories (
    id INT NOT NULL AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    price INT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY(id)
);

INSERT INTO inventories (name, price, quantity) VALUES ("Buku Catatan", 10000, 10);
INSERT INTO inventories (name, price, quantity) VALUES ("Sepatu Gunung", 1500000, 10);
INSERT INTO inventories (name, price, quantity) VALUES ("Jam Tangan Digital", 1000000, 10);
INSERT INTO inventories (name, price, quantity) VALUES ("TWS CleanSound", 500000, 10);
INSERT INTO inventories (name, price, quantity) VALUES ("Adapter Fast Charging mePhone", 500000, 10);