create table user_security_aud (id int8 not null, rev int4 not null, revtype int2, password varchar(255), user_name varchar(255), primary key (id, rev));
create table user_security_roles_aud (rev int4 not null, user_security_id int8 not null, roles varchar(255) not null, revtype int2, primary key (rev, user_security_id, roles));
create table user_security (id int8 not null, password varchar(255), user_name varchar(255), primary key (id));
create table user_security_roles (user_security_id int8 not null, roles varchar(255));
alter table if exists user_security_aud add constraint FK_user_security_aud_TO_revinfo foreign key (rev) references revinfo;
alter table if exists user_security_roles_aud add constraint FK_user_security_roles_aud_TO_revinfo foreign key (rev) references revinfo;
alter table if exists user_security_roles add constraint FK_user_security_roles_TOuser_security foreign key (user_security_id) references user_security;
alter table  user_security add unique (user_name);
