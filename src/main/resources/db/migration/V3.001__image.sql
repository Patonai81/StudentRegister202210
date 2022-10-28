create table image (id int8 not null, location varchar(255), name varchar(255), primary key (id));
create table image_aud (id int8 not null, rev int4 not null, revtype int2, location varchar(255), name varchar(255), primary key (id, rev));
alter table student add image_id int8;
alter table student_aud add  image_id int8;
alter table if exists image_aud add constraint FK_IMAGE_AUD_TO_REVINFO foreign key (rev) references revinfo;

