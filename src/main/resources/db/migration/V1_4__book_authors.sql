create table book_author (
    book uuid not null,
    author uuid not null,

    constraint fk_book foreign key (book) references book(id),
    constraint fk_author foreign key (author) references author(id),
    primary key (book, author)
);

grant select, insert, update on book_author to ${app_user};