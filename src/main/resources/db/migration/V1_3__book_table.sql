SET TIME ZONE 'UTC';

create table book
(
    id                 uuid        not null primary key,
    title              varchar(80) not null,
    total_pages        int         not null,
    price              float       not null,
    isbn               varchar(80) not null unique default gen_random_uuid(),
    published_date     timestamp   not null        default current_timestamp,
    publisher          uuid        not null,

    version            int         not null        default 0,
    created_date       timestamp   not null        default current_timestamp,
    last_modified_date timestamp,

    constraint fk_publisher foreign key (publisher) references publisher (id)
);

grant select, insert, update on book to ${app_user};