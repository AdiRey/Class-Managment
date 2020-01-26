insert into user (id_user, email, first_name, index_number, last_name, major, password, grade)
select 1,'kadrian13@o2.pl', 'Adrian', '160764', 'Kowal','informatyka',
'$2a$10$h4CrhYBIqUP9GHK7F.nJTOVpBCEEHlKCI9fMgRQrd5sYjyWA15YeO','2017/2018'
where not exists (select * from user_roles where roles_id_role = 1); -- password: Mariuszek12

insert into user_role(id_role, name, description)
select 1, 'ROLE_ADMIN', 'administrator' where not exists (select * from user_role where name = 'ROLE_ADMIN');

insert into user_role(id_role, name, description)
select 2, 'ROLE_USER', 'student' where not exists (select * from user_role where name = 'ROLE_USER');

insert into lesson(id_lesson, title, description, start, end)
select 1, 'Javas', 'Siemanko ludzie', '2016-05-16 18:12:47.145482+00', '2016-05-16 20:12:47.145482+00'
where not exists (select * from lesson where id_lesson = 1);

insert into user_roles(users_id_user, roles_id_role) select 1, 1
where not exists (select * from user_roles where users_id_user = 1 and roles_id_role = 1);

insert into user_roles(users_id_user, roles_id_role) select 1, 2
where not exists (select * from user_roles where users_id_user = 1 and roles_id_role = 2);

insert into teacher(id_teacher, first_name, last_name, email, degree)
select 1, 'Maciej', 'Krasny', 'maciej.k@gmail.com', 'inżynier informatyki' where not exists (select * from teacher
where id_teacher = 1);

-- Example users

insert into user (id_user, email, first_name, index_number, last_name, major, password, grade)
values (2,'adrian2@o2.pl', 'Adrian2', '160765', 'Kowal2','informatyka',
'$2a$10$h4CrhYBIqUP9GHK7F.nJTOVpBCEEHlKCI9fMgRQrd5sYjyWA15YeO','2017/2018'); -- password: Mariuszek12

insert into user_roles(users_id_user, roles_id_role) values(2, 2);

insert into user (id_user, email, first_name, index_number, last_name, major, password, grade)
values (3,'adrian3@o2.pl', 'Adrian3', '160766', 'Kowal3','informatyka',
'$2a$10$h4CrhYBIqUP9GHK7F.nJTOVpBCEEHlKCI9fMgRQrd5sYjyWA15YeO','2017/2018'); -- password: Mariuszek12

insert into user_roles(users_id_user, roles_id_role) values(3, 2);

insert into user (id_user, email, first_name, index_number, last_name, major, password, grade)
values (4,'adrian4@o2.pl', 'Adrian4', '160767', 'Kowal4','informatyka',
'$2a$10$h4CrhYBIqUP9GHK7F.nJTOVpBCEEHlKCI9fMgRQrd5sYjyWA15YeO','2017/2018'); -- password: Mariuszek12

insert into user_roles(users_id_user, roles_id_role) values(4, 2);

insert into user (id_user, email, first_name, index_number, last_name, major, password, grade)
values (5,'adrian5@o2.pl', 'Adrian5', '160768', 'Kowal5','informatyka',
'$2a$10$h4CrhYBIqUP9GHK7F.nJTOVpBCEEHlKCI9fMgRQrd5sYjyWA15YeO','2017/2018'); -- password: Mariuszek12

insert into user_roles(users_id_user, roles_id_role) values(5, 2);

insert into user (id_user, email, first_name, index_number, last_name, major, password, grade)
values (6,'adrian6@o2.pl', 'Adrian6', '160769', 'Kowal6','informatyka',
'$2a$10$h4CrhYBIqUP9GHK7F.nJTOVpBCEEHlKCI9fMgRQrd5sYjyWA15YeO','2017/2018'); -- password: Mariuszek12

insert into user_roles(users_id_user, roles_id_role) values(6, 2);

insert into user (id_user, email, first_name, index_number, last_name, major, password, grade)
values (7,'adrian7@o2.pl', 'Adrian7', '160770', 'Kowal7','informatyka',
'$2a$10$h4CrhYBIqUP9GHK7F.nJTOVpBCEEHlKCI9fMgRQrd5sYjyWA15YeO','2017/2018'); -- password: Mariuszek12

insert into user_roles(users_id_user, roles_id_role) values(7, 2);

insert into user (id_user, email, first_name, index_number, last_name, major, password, grade)
values (8,'adrian8@o2.pl', 'Adrian8', '160771', 'Kowal8','informatyka',
'$2a$10$h4CrhYBIqUP9GHK7F.nJTOVpBCEEHlKCI9fMgRQrd5sYjyWA15YeO','2017/2018'); -- password: Mariuszek12

insert into user_roles(users_id_user, roles_id_role) values(8, 2);

insert into user (id_user, email, first_name, index_number, last_name, major, password, grade)
values (9,'adrian9@o2.pl', 'Adrian9', '160772', 'Kowal9','informatyka',
'$2a$10$h4CrhYBIqUP9GHK7F.nJTOVpBCEEHlKCI9fMgRQrd5sYjyWA15YeO','2017/2018'); -- password: Mariuszek12

insert into user_roles(users_id_user, roles_id_role) values(9, 2);

insert into user (id_user, email, first_name, index_number, last_name, major, password, grade)
values (10,'adrian10@o2.pl', 'Adrian10', '160773', 'Kowal10','informatyka',
'$2a$10$h4CrhYBIqUP9GHK7F.nJTOVpBCEEHlKCI9fMgRQrd5sYjyWA15YeO','2017/2018'); -- password: Mariuszek12

insert into user_roles(users_id_user, roles_id_role) values(10, 2);

insert into user (id_user, email, first_name, index_number, last_name, major, password, grade)
values (11,'adrian11@o2.pl', 'Adrian11', '160774', 'Kowal11','informatyka',
'$2a$s10$h4CrhYBIqUP9GHK7F.nJTOVpBCEEHlKCI9fMgRQrd5sYjyWA15YeO','2017/2018'); -- password: Mariuszek12

insert into user_roles(users_id_user, roles_id_role) values(11, 2);

insert into user (id_user, email, first_name, index_number, last_name, major, password, grade)
values (12,'adrian12@o2.pl', 'Adrian12', '160775', 'Kowal12','informatyka',
'$2a$10$h4CrhYBIqUP9GHK7F.nJTOVpBCEEHlKCI9fMgRQrd5sYjyWA15YeO','2017/2018'); -- password: Mariuszek12

insert into user_roles(users_id_user, roles_id_role) values(12, 2);

insert into user (id_user, email, first_name, index_number, last_name, major, password, grade)
values (13,'adrian13@o2.pl', 'Adrian13', '160776', 'Kowal13','informatyka',
'$2a$10$h4CrhYBIqUP9GHK7F.nJTOVpBCEEHlKCI9fMgRQrd5sYjyWA15YeO','2017/2018'); -- password: Mariuszek12

insert into user_roles(users_id_user, roles_id_role) values(13, 2);

insert into user (id_user, email, first_name, index_number, last_name, major, password, grade)
values (14,'adrian14@o2.pl', 'Adrian14', '160777', 'Kowal14','informatyka',
'$2a$10$h4CrhYBIqUP9GHK7F.nJTOVpBCEEHlKCI9fMgRQrd5sYjyWA15YeO','2017/2018'); -- password: Mariuszek12

insert into user_roles(users_id_user, roles_id_role) values(14, 2);

insert into user (id_user, email, first_name, index_number, last_name, major, password, grade)
values (15,'adrian15@o2.pl', 'Adrian15', '160778', 'Kowal15','informatyka',
'$2a$10$h4CrhYBIqUP9GHK7F.nJTOVpBCEEHlKCI9fMgRQrd5sYjyWA15YeO','2017/2018'); -- password: Mariuszek12

insert into user_roles(users_id_user, roles_id_role) values(15, 2);

-- example lessons

insert into lesson(id_lesson, title, description, start, end)
values (2, 'Javas', 'Siemanko ludzie2', '2016-05-17 18:12:47.145482+00', '2016-05-17 20:12:47.145482+00');

insert into lesson(id_lesson, title, description, start, end)
values (3, 'Javas', 'Siemanko ludzie3', '2016-05-18 18:12:47.145482+00', '2016-05-18 20:12:47.145482+00');

insert into lesson(id_lesson, title, description, start, end)
values (4, 'Javas', 'Siemanko ludzie4', '2016-05-19 18:12:47.145482+00', '2016-05-19 20:12:47.145482+00');

insert into lesson(id_lesson, title, description, start, end)
values (5, 'Javas', 'Siemanko ludzie5', '2016-05-20 18:12:47.145482+00', '2016-05-20 20:12:47.145482+00');

insert into lesson(id_lesson, title, description, start, end)
values (6, 'Javas', 'Siemanko ludzie6', '2016-05-20 18:12:47.145482+00', '2016-05-20 20:12:47.145482+00');

insert into lesson(id_lesson, title, description, start, end)
values (7, 'Javas', 'Siemanko ludzie7', '2016-05-21 18:12:47.145482+00', '2016-05-21 20:12:47.145482+00');

insert into lesson(id_lesson, title, description, start, end)
values (8, 'Javas', 'Siemanko ludzie8', '2016-05-22 18:12:47.145482+00', '2016-05-22 20:12:47.145482+00');

insert into lesson(id_lesson, title, description, start, end)
values (9, 'Javas', 'Siemanko ludzie9', '2016-05-23 18:12:47.145482+00', '2016-05-23 20:12:47.145482+00');

insert into lesson(id_lesson, title, description, start, end)
values (10, 'Javas', 'Siemanko ludzie10', '2016-05-24 18:12:47.145482+00', '2016-05-24 20:12:47.145482+00');

-- Example teacher

insert into teacher(id_teacher, first_name, last_name, email, degree)
values (2, 'Maciej2', 'Krasny2', 'maciej.k2@gmail.com', 'inżynier informatyki');

insert into teacher(id_teacher, first_name, last_name, email, degree)
values (3, 'Maciej3', 'Krasny3', 'maciej.k3@gmail.com', 'inżynier informatyki');

insert into teacher(id_teacher, first_name, last_name, email, degree)
values (4, 'Maciej4', 'Krasny4', 'maciej.k4@gmail.com', 'inżynier informatyki');

insert into teacher(id_teacher, first_name, last_name, email, degree)
values (5, 'Maciej5', 'Krasny5', 'maciej.k5@gmail.com', 'inżynier informatyki');

insert into teacher(id_teacher, first_name, last_name, email, degree)
values (6, 'Maciej6', 'Krasny6', 'maciej.k6@gmail.com', 'inżynier informatyki');

insert into teacher(id_teacher, first_name, last_name, email, degree)
values (7, 'Maciej7', 'Krasny7', 'maciej.k7@gmail.com', 'inżynier informatyki');

insert into teacher(id_teacher, first_name, last_name, email, degree)
values (8, 'Maciej8', 'Krasny8', 'maciej.k8@gmail.com', 'inżynier informatyki');

insert into teacher(id_teacher, first_name, last_name, email, degree)
values (9, 'Maciej9', 'Krasny9', 'maciej.k9@gmail.com', 'inżynier informatyki');

insert into teacher(id_teacher, first_name, last_name, email, degree)
values (10, 'Maciej10', 'Krasny10', 'maciej.k10@gmail.com', 'inżynier informatyki');