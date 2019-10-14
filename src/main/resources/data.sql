insert into user(user_id, email, first_name, index_number, last_name, major, password, year) values
(1, 'maciek@tlen.pl','Maciej', '160763' ,'Kowal', 'informatyka', 'maciekxa1', '2018/2019'),
(2, 'adekx15@tlen.pl','Adrian', '160764' ,'Kowal', 'bezpieczeństwo wewnętrzne', 'adikozak2', '2017/2018');

insert into user_role(role_id, role, description) values
(1, 'ROLE_ADMIN', 'administrator'),
(2, 'ROLE_USER', 'student');

insert into lesson(lesson_id, title, description, start, end) value
(1, 'Javas', 'Siemanko ludzie', '2016-05-16 18:12:47.145482+00', '2016-05-16 20:12:47.145482+00')