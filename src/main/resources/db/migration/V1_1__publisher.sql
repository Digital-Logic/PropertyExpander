create table publisher (
    id uuid not null primary key,
    name varchar(80) not null,

    version            int         not null default 0,
    created_date       timestamp   not null default current_timestamp,
    last_modified_date timestamp
);

create unique index idx_publisher_name on publisher (upper(name));

grant select,insert, update on publisher to ${app_user};