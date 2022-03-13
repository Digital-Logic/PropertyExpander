create table employee
(
    id                 UUID        NOT NULL PRIMARY KEY,
    email              VARCHAR(80) NOT NULL,
    first_name         varchar(40) not null,
    last_name          varchar(40) not null,
    department         uuid        not null,
    job                uuid        not null,
    salary             float       not null,

    version            int         not null default 0,
    created_date       timestamp   not null default current_timestamp,
    last_modified_date timestamp,

    constraint fk_department foreign key (department) references department (id),
    constraint fk_job foreign key (job) references job (id)
);

grant select, insert, update on employee to ${app_user};