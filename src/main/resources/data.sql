INSERT INTO USER(id, name, email, password, profile, is_active) values(1, 'Administrator', 'admin@mail.com', '$2a$10$fBVfqooCFheIBubwGCNIEuqVV66gGEhLqxa/dW.cLT3CLPTuXTlX.', 'ADMINISTRATOR', true);
INSERT INTO administrator(id) values(1);

INSERT INTO USER(id, name, email, password, profile, is_active) values(2, 'Customer', 'customer@mail.com', '$2a$10$TIiSG3Rv2He1V42QfMU5n.JFwDmx/N8e6oOUxPKn5h8jFQJbRao1m', 'CUSTOMER', true);
INSERT INTO customer(id, cpf) values(2, '321.458.121-45');

INSERT INTO USER(id, name, email, password, profile, is_active) values(3, 'Company', 'company@mail.com', '$2a$10$TIiSG3Rv2He1V42QfMU5n.JFwDmx/N8e6oOUxPKn5h8jFQJbRao1m', 'COMPANY', true);
INSERT INTO company(id, registered_number, phone) values(3, '32.458.121/1234-45', '(035) 12345-6789');

INSERT INTO product_category(name) values('Móveis');
INSERT INTO product_category(name) values('Eletrodomésticos');
INSERT INTO product_category(name) values('Tecnologia');
INSERT INTO product_category(name) values('Brinquedos');
INSERT INTO product_category(name) values('Ferramentas');
INSERT INTO product_category(name) values('Moda');
