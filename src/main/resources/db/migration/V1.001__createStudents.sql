create sequence hibernate_sequence start 1 increment 1;
create table student (id int8 not null, birth_date date not null, name varchar(255) not null, semester int4 not null, primary key (id));
