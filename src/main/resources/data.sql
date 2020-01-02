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
