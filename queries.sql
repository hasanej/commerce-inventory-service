CREATE TABLE inventories (
    id INT NOT NULL AUTO_INCREMENT,
    product_id INT,
    product_name VARCHAR(200) NOT NULL,
    price INT NOT NULL,
    quantity INT NOT NULL,
    PRIMARY KEY(id)
);

INSERT INTO inventories (product_id, product_name, price, quantity) VALUES (11, 'Buku Catatan', 10000, 10);
INSERT INTO inventories (product_id, product_name, price, quantity) VALUES (21, 'Sepatu Gunung', 1500000, 10);
INSERT INTO inventories (product_id, product_name, price, quantity) VALUES (31, 'Jam Tangan Digital', 1000000, 10);
INSERT INTO inventories (product_id, product_name, price, quantity) VALUES (41, 'TWS CleanSound', 500000, 10);
INSERT INTO inventories (product_id, product_name, price, quantity) VALUES (51, 'Adapter Fast Charging mePhone', 500000, 10);
