INSERT INTO users (username, password, enabled, nombre, apellido, email) VALUES ('julio_osorio', '$2a$10$huHVJ6BAn1MQi1TfEqYrMe0gs125bt2dOBQOmwVjx1szH65QfkTCa', TRUE, 'Julio', 'Osorio', 'julio@mail.com');
INSERT INTO users (username, password, enabled, nombre, apellido, email) VALUES ('luisa_severino', '$2a$10$huHVJ6BAn1MQi1TfEqYrMe0gs125bt2dOBQOmwVjx1szH65QfkTCa', TRUE, 'Luisa', 'Severino', 'luisa@mail.com');

INSERT INTO roles (nombre) VALUES ('ROLE_ADMIN');
INSERT INTO roles (nombre) VALUES ('ROLE_USER');

INSERT INTO users_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO users_roles (user_id, role_id) VALUES (1, 2);
INSERT INTO users_roles (user_id, role_id) VALUES (2, 2);