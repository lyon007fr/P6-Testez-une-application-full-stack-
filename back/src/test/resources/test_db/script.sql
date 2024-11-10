-- Supprimer les tables si elles existent
DROP TABLE IF EXISTS `PARTICIPATE`;
DROP TABLE IF EXISTS `SESSIONS`;
DROP TABLE IF EXISTS `TEACHERS`;
DROP TABLE IF EXISTS `USERS`;


CREATE TABLE `TEACHERS` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `last_name` VARCHAR(40),
  `first_name` VARCHAR(40),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `SESSIONS` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(50),
  `description` VARCHAR(2000),
  `date` TIMESTAMP,
  `teacher_id` int,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `USERS` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `last_name` VARCHAR(40),
  `first_name` VARCHAR(40),
  `admin` BOOLEAN NOT NULL DEFAULT false,
  `email` VARCHAR(255),
  `password` VARCHAR(255),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE `PARTICIPATE` (
  `user_id` INT, 
  `session_id` INT
);

ALTER TABLE `SESSIONS` ADD FOREIGN KEY (`teacher_id`) REFERENCES `TEACHERS` (`id`);
ALTER TABLE `PARTICIPATE` ADD FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`);
ALTER TABLE `PARTICIPATE` ADD FOREIGN KEY (`session_id`) REFERENCES `SESSIONS` (`id`);

INSERT INTO TEACHERS (first_name, last_name)
VALUES ('Margot', 'DELAHAYE'),
       ('Hélène', 'THIERCELIN');


INSERT INTO USERS (first_name, last_name, admin, email, password, created_at, updated_at)
VALUES ('Admin', 'Admin', true, 'yoga@studio.com', '$2a$10$.Hsa/ZjUVaHqi0tp9xieMeewrnZxrZ5pQRzddUXE/WjDu2ZThe6Iq',NOW(), NOW()),
        ('Alice', 'DUPONT', false, 'test@studi.com', '$2a$10$Hk8nZJ0gVh0N3Q7p00Q5XeJuqHkW4Ew2P0I7cfSr8Z9r.YZVR5A8y',NOW(), NOW()), -- password: toto123456
        ('Bob', 'DURAND', false, 'toto@studio.com', '$2b$12$Q0gvnAeb7ZTk9HvQmvaNmekeOMr3AKDAvbEorFngo9NbhmtDlo5aC',NOW(), NOW()), -- password: test1
        ('Charlie', 'DUPUIS', false, 'tata@studio.com','$2a$12$4Akqto4/45Amdwc8Wi4YFuPiK2CPPqGcl1qouoXb.w6qtnsW8ERpG',NOW(), NOW()); -- password: test256784

SELECT 'Script executed successfully' AS message;
INSERT INTO SESSIONS (name, description, date, teacher_id)
VALUES ('Session matinal Yoga', 'Cours de yoga pour débutant', '2020-01-01 10:00:00', 1),
       ('Session dejeuner Yoga', 'Cours de yoga pour intermédiaire', '2025-01-01 12:00:00', 1),
       ('Session diner Yoga', 'Cours de yoga pour avancé', '2023-01-01 14:00:00', 1),
       ('Session dimanche Yoga', 'Cours de yoga pour débutant', '2022-01-02 10:00:00', 2),
       ('Yoga du lundi', 'Cours de yoga pour intermédiaire', '2024-01-02 12:00:00', 2),
       ('Yoga du mardi', 'Cours de yoga pour avancé', '2024-01-02 14:00:00', 2);

INSERT INTO PARTICIPATE (user_id, session_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (3, 4),
       (3, 5),
       (4, 6);
