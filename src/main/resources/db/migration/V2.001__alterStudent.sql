alter table patonaiszabolcs.public.student add column external_id int4;
alter table patonaiszabolcs.public.student add column used_free_semesters int4 not null default 0;

