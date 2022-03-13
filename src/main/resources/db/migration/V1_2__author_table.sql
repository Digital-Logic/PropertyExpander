create table author (
    id uuid not null primary key,
    first_name varchar(40) not null,
    last_name varchar(40) not null,
    email varchar(80) not null,

    version int not null default 0,
    created_date timestamp not null default current_timestamp,
    last_modified_date timestamp
);

create unique index idx_unique_email on author (upper(email));

grant select, insert, update on author to ${app_user};