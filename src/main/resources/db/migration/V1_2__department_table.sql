create table department (
    id uuid not null primary key,
    name varchar(40) not null
);

grant select, insert, update on department to ${app_user};