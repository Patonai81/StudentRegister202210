create sequence hibernate_sequence start 1 increment 1;
create table course (id int8 not null, name varchar(255) not null, primary key (id));
create table student (id int8 not null, birth_date date not null, name varchar(255) not null, semester int4 not null, primary key (id));
create table student_course (fk_student_id int8 not null, fk_course_id int8 not null, primary key (fk_student_id, fk_course_id));
create table teacher (id int8 not null, birth_date date not null, name varchar(255) not null, primary key (id));
create table teacher_course (fk_teacher_id int8 not null, fk_course_id int8 not null, primary key (fk_teacher_id, fk_course_id));
alter table if exists student_course add constraint FK44ywy96s9lj9ayxo1kw6lviyk foreign key (fk_course_id) references course;
alter table if exists student_course add constraint FK301u6vomnhrsew7yj30r0lxwi foreign key (fk_student_id) references student;
alter table if exists teacher_course add constraint FK40yvh6rnm26y0evgsye9g4bi5 foreign key (fk_course_id) references course;
alter table if exists teacher_course add constraint FKrcbeiujcq70rblpjyt792opbo foreign key (fk_teacher_id) references teacher;
create sequence hibernate_sequence start 1 increment 1;
create table course (id int8 not null, name varchar(255) not null, primary key (id));
create table student (id int8 not null, birth_date date not null, name varchar(255) not null, semester int4 not null, primary key (id));
create table student_course (fk_student_id int8 not null, fk_course_id int8 not null, primary key (fk_student_id, fk_course_id));
create table teacher (id int8 not null, birth_date date not null, name varchar(255) not null, primary key (id));
create table teacher_course (fk_teacher_id int8 not null, fk_course_id int8 not null, primary key (fk_teacher_id, fk_course_id));
alter table if exists student_course add constraint FK44ywy96s9lj9ayxo1kw6lviyk foreign key (fk_course_id) references course;
alter table if exists student_course add constraint FK301u6vomnhrsew7yj30r0lxwi foreign key (fk_student_id) references student;
alter table if exists teacher_course add constraint FK40yvh6rnm26y0evgsye9g4bi5 foreign key (fk_course_id) references course;
alter table if exists teacher_course add constraint FKrcbeiujcq70rblpjyt792opbo foreign key (fk_teacher_id) references teacher;
