INSERT INTO users (email, first_name, last_name, password) VALUES
('alice@example.com', 'Alice', 'Johnson', 'alice123'),
('bob@example.com', 'Bob', 'Smith', 'bob456'),
('charlie@example.com', 'Charlie', 'Brown', 'charlie789');

INSERT INTO tasks (
title, body, status, created_at, accomplished_at, owner_id, accomplished_by_id
) VALUES
('Build API', 'Develop the backend API using Spring Boot', 'DONE', '2025-07-01 10:00:00', '2025-07-02 18:00:00', 1, 2),
('Write Docs', 'Document all the REST endpoints', 'TODO', '2025-07-05 12:00:00', NULL, 1, NULL),
('Design UI', 'Create a responsive UI layout', 'TODO', '2025-07-10 09:30:00', NULL, 2, NULL),
('Fix Bugs', 'Resolve critical issues found during testing', 'DONE', '2025-07-12 14:00:00', '2025-07-13 17:00:00', 3, 1);

INSERT INTO users_tasks (user_id, task_id) VALUES
(1, 1),
(1, 2),
(2, 1),
(2, 3),
(3, 4),
(3, 2);