create table dependents (
  id    uuid    not null primary key,
  first_name varchar(40) not null,
  last_name varchar(40) not null,
  relationship varchar(40) not null,
  employee_id uuid not null,

  constraint fk_employee_table foreign key (employee_id) references employee(id)
);

grant select, insert, update on dependents to ${app_user};