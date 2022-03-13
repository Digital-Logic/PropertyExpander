SET TIME ZONE 'UTC';

create table job (
    id uuid     not null primary key,
    title varchar(40) not null,
    min_salary  float not null,
    max_salary float not null
);

grant select, insert, update on job to ${app_user};