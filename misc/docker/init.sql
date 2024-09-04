CREATE TABLE IF NOT EXISTS product_category (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL
);

CREATE TABLE IF NOT EXISTS subcategory (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(250) NOT NULL,
    product_category_id BIGINT,
    FOREIGN KEY (product_category_id) REFERENCES product_category(id)
);

INSERT INTO product_category(name) VALUES ('Móveis');
INSERT INTO product_category(name) VALUES ('Eletrodomésticos');
INSERT INTO product_category(name) VALUES ('Tecnologia');
INSERT INTO product_category(name) VALUES ('Brinquedos');
INSERT INTO product_category(name) VALUES ('Ferramentas');
INSERT INTO product_category(name) VALUES ('Moda');

INSERT INTO subcategory(name, product_category_id) VALUES ('Sofá', 1);
INSERT INTO subcategory(name, product_category_id) VALUES ('Mesa', 1);
INSERT INTO subcategory(name, product_category_id) VALUES ('Batedeira', 2);
INSERT INTO subcategory(name, product_category_id) VALUES ('Liquidificador', 2);
INSERT INTO subcategory(name, product_category_id) VALUES ('Sanduicheira', 2);
INSERT INTO subcategory(name, product_category_id) VALUES ('Notebook', 3);
INSERT INTO subcategory(name, product_category_id) VALUES ('Netbook', 3);
INSERT INTO subcategory(name, product_category_id) VALUES ('Desktop', 3);
INSERT INTO subcategory(name, product_category_id) VALUES ('Lego Star Wars', 4);
INSERT INTO subcategory(name, product_category_id) VALUES ('Alicate', 5);
INSERT INTO subcategory(name, product_category_id) VALUES ('Chave de fenda', 5);
INSERT INTO subcategory(name, product_category_id) VALUES ('Chave de boca', 5);
INSERT INTO subcategory(name, product_category_id) VALUES ('Furadeiras', 5);
INSERT INTO subcategory(name, product_category_id) VALUES ('Camisa', 6);
INSERT INTO subcategory(name, product_category_id) VALUES ('Calça', 6);
INSERT INTO subcategory(name, product_category_id) VALUES ('Saia', 6);
INSERT INTO subcategory(name, product_category_id) VALUES ('Vestido', 6);
INSERT INTO subcategory(name, product_category_id) VALUES ('Camiseta', 6);