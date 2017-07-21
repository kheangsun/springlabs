INSERT INTO role (id, role_name) VALUES (1, 'ROLE_ADMIN');
INSERT INTO role (id, role_name) VALUES (2, 'ROLE_USER');

INSERT INTO user (id, first_name, last_name, email, password, username, role_id) 
VALUES (1, 'Nizar', 'Ellouze','nizarellouze@gmail.com', '$2a$10$bpNMKeaQXKpJ4JVxOHWvu.tZdmCLT9nKcZreJ/ELfCgmTCyhC7GPy', 'admin', 1);
INSERT INTO user (id, first_name, last_name, email, password, username, role_id)
VALUES (2, 'Omar', 'Kessemtini', 'kessemtini.omar@gmail.com', '$2a$10$TA.UfUqLa8uDeGkt95FfLeq7T5Y5vpDpzAtvJrHSLzLliY/PARXUq', 'user', 2);