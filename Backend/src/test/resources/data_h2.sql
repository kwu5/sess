INSERT INTO PERSON(PERSON_ID, NAME, ROLE, PASSWORD) 
VALUES 
('30','john', 'USER','$2a$10$UOnMXzg2DrElZjDXuDOlQeIqILZzn2EPQJv1YuTOPEyQgjLeEPRhy'),
('31','jane', 'USER','$2a$10$0T6F3ebKo4peKLAB6TnOIe8HQYteYZC03vybg5ksEVdKnnEXbo0c6'),
('32','kyle', 'ADMIN','$2a$10$X9JpoJZJHWgN/4sLqPhKKuk2YSm.OfZgCz2Qssgsiu6F09MJpwh16');




INSERT INTO CLIENT(CLIENT_ID,FIRST_NAME,LAST_NAME,PHONE,ADDRESS,CITY,ZIPCODE,REGISTRATION_TIME,FOLDER_URL,COMMENT)
VALUES 
('50','kyle','doe','4151234567','322 Hello st','San Francisco','94116','2024-01-01 10:00:00', 'www.google.com/x',NULL),
('51','Ben','doe','4151234567','322 Hello st','San Francisco','94116','2023-11-01 10:12:00', 'www.google.com/y','yes comment'),
('52','Ben','Joe','4159876543','233 world st','San Francisco','94123','2024-02-01 16:24:00',NULL,NULL),
('53','Jane','doe',NULL,'5111 world st, San Franciscon, CA, 94108','San Francisco','84213','2023-11-01 10:12:00', 'www.google.com/z','some comment');




INSERT INTO TASK(TASK_ID, START_TIME,END_TIME, OWNER, CLIENT, LOCATION, TYPE, DESCRIPTION) VALUES (99,  '2024-01-01 10:00:00','2024-01-01 11:00:00',30,50,'3333 hell st, San Francisco, CA, 94444','appointment','happy day');
INSERT INTO TASK(TASK_ID, START_TIME,END_TIME, OWNER, CLIENT, LOCATION, TYPE) VALUES (100,  '2024-02-01 15:00:00','2024-02-01 19:00:00',30,50,'3333 hell st, San Francisco, CA, 94444','appointment');
INSERT INTO TASK(TASK_ID, START_TIME,END_TIME, OWNER, CLIENT, LOCATION, TYPE, DESCRIPTION) VALUES (101,  '2024-03-01 10:00:00','2024-03-01 11:00:00',31,51,'321 hell st, San Francisco, CA, 94444','appointment','happy every day');
INSERT INTO TASK(TASK_ID, START_TIME,END_TIME, OWNER, LOCATION, TYPE, DESCRIPTION) VALUES (102,  CURRENT_TIMESTAMP,CURRENT_TIMESTAMP,30,'1234 heaven st, San Francisco, CA, 96666','event','happy every event');




