CREATE SEQUENCE hibernate_sequence STart 100;
CREATE TABLE Person(
    id integer primary key,
    name varchar(50)
);
insert into Person (id, name)
values (1, 'Vlad'),
       (2, 'Sasha'),
       (3, 'Andrey'),
       (4, 'Anton'),
       (5, 'Vania');