create table authors (
    id bigserial primary key,
    full_name varchar(255)
);

create table genres (
    id bigserial primary key,
    name varchar(255)
);

create table books (
    id bigserial primary key,
    title varchar(255),
    author_id bigint references authors (id) on delete cascade,
    genre_id bigint references genres(id) on delete cascade
);

create table comments (
    id bigserial primary key,
    text varchar(1000),
    book_id bigint references books (id) on delete cascade
);