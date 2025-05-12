-- ========== Insert Courses ==========
INSERT INTO course (id, code, name, description, fee, duration) VALUES
(1, 'JAVA101', 'Java', 'Comprehensive Java course covering OOPs, Collections, and Streams.', 10000.00, '3'),
(2, 'SPRING102', 'Spring Boot', 'Spring Boot course covering REST APIs, Microservices, and Security.', 12000.00, '2'),
(3, 'REACT103', 'React', 'React course covering hooks, state management, and advanced components.', 15000.00, '5');

-- ========== Insert Mentors ==========
INSERT INTO mentor (id, name, expertise, email, password) VALUES
(1, 'John Doe', 'Java', 'john.doe@example.com', 'password123'),
(2, 'Jane Smith', 'Spring Boot', 'jane.smith@example.com', 'password123'),
(3, 'Mark Lee', 'React', 'mark.lee@example.com', 'password123');

-- ========== Insert Students ==========
INSERT INTO student (id, name, email, password) VALUES
(1, 'Alice Brown', 'alice@example.com', 'password123'),
(2, 'Bob Green', 'bob@example.com', 'password123'),
(3, 'Charlie Black', 'charlie@example.com', 'password123'),
(4, 'Nikhil', 'nikhil@example.com', 'password123'),
(5, 'Rahul', 'rahul@example.com', 'password123'),
(6, 'Ram', 'ram@example.com', 'password123');

-- ========== Insert Class Schedules (without student assignment) ==========
INSERT INTO class_schedule (id, course_id, mentor_id, time_slot, status) VALUES
(1, 1, 1, '07:00:00', 'NEW'),
(2, 2, 2, '08:00:00', 'WIP'),
(3, 3, 3, '09:00:00', 'COMPLETED');

-- ========== Link Schedules with Students ==========
INSERT INTO class_schedule_student (class_schedule_id, student_id) VALUES
(1, 1),
(2, 2),
(3, 3);

-- ========== Insert Users ==========
-- Email must match entity field `email`, not `username`
INSERT INTO users (email, password, role) VALUES
('student1@example.com', 'studentpass', 'STUDENT'),
('mentor1@example.com', 'mentorpass', 'MENTOR');
