create table book_genre (

    book uuid not null,
    genre uuid not null,

    constraint fk_book foreign key (book) references book(id),
    constraint fk_genre foreign key (genre) references genre(id)
);

grant select, insert, update on book_genre to ${app_user};