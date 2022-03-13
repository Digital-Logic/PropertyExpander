create table genre (

    id uuid not null primary key,
    name varchar(30) not null,

    version            int       not null default 0,
    created_date       timestamp not null default current_timestamp,
    last_modified_date timestamp
);

create unique index idx_genre_name on genre (upper(name));

grant select, insert, update on genre to ${app_user};