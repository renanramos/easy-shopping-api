DROP TABLE IF EXISTS user;
INSERT INTO user(id, email, name, password, profile) VALUES (1, 'admin@mail.com', 'Administrator', '$2a$10$fBVfqooCFheIBubwGCNIEuqVV66gGEhLqxa/dW.cLT3CLPTuXTlX.', 'ADMINISTRATOR');
INSERT INTO administrator(id) VALUES(1);
