create table course (id int8 not null, name varchar(255) not null, primary key (id));
create table student_course (fk_student_id int8 not null, fk_course_id int8 not null, primary key (fk_student_id, fk_course_id));
create table teacher_course (fk_teacher_id int8 not null, fk_course_id int8 not null, primary key (fk_teacher_id, fk_course_id));

alter table if exists student_course add constraint FK_COURSE_TOSTUDENT_COURSEID foreign key (fk_course_id) references course;
alter table if exists student_course add constraint FK_COURSE_TOSTUDENT_STUDENTID foreign key (fk_student_id) references student;
alter table if exists teacher_course add constraint FK_TEACHER_TOCOURSE_COURSEID foreign key (fk_course_id) references course;
alter table if exists teacher_course add constraint FK_TEACHER_TOCOURSE_TEACHERID foreign key (fk_teacher_id) references teacher;
