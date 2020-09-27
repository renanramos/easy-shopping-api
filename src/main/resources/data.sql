INSERT INTO USER(id, name, email, password, profile, is_active, provider) values(1, 'Administrator', 'admin@mail.com', '$2a$10$fBVfqooCFheIBubwGCNIEuqVV66gGEhLqxa/dW.cLT3CLPTuXTlX.', 'ADMINISTRATOR', true, 'LOCAL');
INSERT INTO administrator(id) values(1);

INSERT INTO USER(id, name, email, password, profile, is_active, provider) values(2, 'Customer', 'customer@mail.com', '$2a$10$TIiSG3Rv2He1V42QfMU5n.JFwDmx/N8e6oOUxPKn5h8jFQJbRao1m', 'CUSTOMER', true, 'LOCAL');
INSERT INTO customer(id, cpf) values(2, '321.458.121-45');

INSERT INTO USER(id, name, email, password, profile, is_active, provider) values(3, 'Company', 'company@mail.com', '$2a$10$TIiSG3Rv2He1V42QfMU5n.JFwDmx/N8e6oOUxPKn5h8jFQJbRao1m', 'COMPANY', true, 'LOCAL');
INSERT INTO company(id, registered_number, phone) values(3, '32.458.121/1234-45', '(035) 12345-6789');

INSERT INTO product_category(name) values('Móveis');
INSERT INTO product_category(name) values('Eletrodomésticos');
INSERT INTO product_category(name) values('Tecnologia');
INSERT INTO product_category(name) values('Brinquedos');
INSERT INTO product_category(name) values('Ferramentas');
INSERT INTO product_category(name) values('Moda');

INSERT INTO subcategory(name, product_category_id) values('Sofá', 1);
INSERT INTO subcategory(name, product_category_id) values('Mesa', 1);
INSERT INTO subcategory(name, product_category_id) values('Batedeira', 2);
INSERT INTO subcategory(name, product_category_id) values('Liquidificador', 2);
INSERT INTO subcategory(name, product_category_id) values('Sanduicheira', 2);
INSERT INTO subcategory(name, product_category_id) values('Notebook', 3);
INSERT INTO subcategory(name, product_category_id) values('Netbook', 3);
INSERT INTO subcategory(name, product_category_id) values('Desktop', 3);
INSERT INTO subcategory(name, product_category_id) values('Lego Star Wars', 4);
INSERT INTO subcategory(name, product_category_id) values('Alicate', 5);
INSERT INTO subcategory(name, product_category_id) values('Chave de fenda', 5);
INSERT INTO subcategory(name, product_category_id) values('Chave de boca', 5);
INSERT INTO subcategory(name, product_category_id) values('Furadeiras', 5);
INSERT INTO subcategory(name, product_category_id) values('Camisa', 6);
INSERT INTO subcategory(name, product_category_id) values('Calça', 6);
INSERT INTO subcategory(name, product_category_id) values('Saia', 6);
INSERT INTO subcategory(name, product_category_id) values('Vestido', 6);
INSERT INTO subcategory(name, product_category_id) values('Camiseta', 6);

INSERT INTO store(name, corporate_name, registered_number, company_id) values('Loja', 'Loja 1', '12.123.123/1234-12', 3);

INSERT INTO product(description, name, price, company_id, subcategory_id, store_id) values('Liquidificador de uma marca diferenciada', 'Liquidificador', 120.5, 3, 4, 1);