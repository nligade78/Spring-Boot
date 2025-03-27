-- Insert Courses
-- Insert Courses
INSERT INTO course (id, code, name, description, fee, duration)
VALUES (1, 'JAVA101', 'Java', 'Comprehensive Java course covering OOPs, Collections, and Streams.', 10000, 3);

INSERT INTO course (id, code, name, description, fee, duration)
VALUES (2, 'SPRING102', 'Spring Boot', 'Spring Boot course covering REST APIs, Microservices, and Security.', 12000, 2);

INSERT INTO course (id, code, name, description, fee, duration)
VALUES (3, 'REACT103', 'React', 'React course covering hooks, state management, and advanced components.', 15000, 5);


-- Insert Mentors
INSERT INTO mentor (id, name, expertise) VALUES (1, 'John Doe', 'Java');
INSERT INTO mentor (id, name, expertise) VALUES (2, 'Jane Smith', 'Spring Boot');
INSERT INTO mentor (id, name, expertise) VALUES (3, 'Mark Lee', 'React');


-- Insert Students
INSERT INTO student (id, name, email)
VALUES (1, 'Alice Brown', 'alice@example.com');

INSERT INTO student (id, name, email)
VALUES (2, 'Bob Green', 'bob@example.com');

INSERT INTO student (id, name, email)
VALUES (3, 'Charlie Black', 'charlie@example.com');

INSERT INTO student (id, name, email)
VALUES (4, 'Nikhil', 'nikhil@example.com');

INSERT INTO student (id, name, email)
VALUES (5, 'Rahul', 'rahul@example.com');

INSERT INTO student (id, name, email)
VALUES (6, 'Ram', 'ram@example.com');



-- Insert into the main class_schedule table (without student_id)
INSERT INTO class_schedule (course_id, mentor_id, time_slot, status)
VALUES (1, 1, '07:00:00', 'NEW');

INSERT INTO class_schedule (course_id, mentor_id, time_slot, status)
VALUES (2, 2, '08:00:00', 'WIP');

INSERT INTO class_schedule (course_id, mentor_id, time_slot, status)
VALUES (3, 3, '09:00:00', 'COMPLETED');


-- Insert into the join table to link schedules with students
INSERT INTO class_schedule_student (class_schedule_id, student_id) VALUES (1, 1);
INSERT INTO class_schedule_student (class_schedule_id, student_id) VALUES (2, 2);
INSERT INTO class_schedule_student (class_schedule_id, student_id) VALUES (3, 3);


