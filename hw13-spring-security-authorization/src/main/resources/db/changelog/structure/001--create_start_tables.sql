create table roles (
    id bigserial,
    name varchar(50),
    primary key (id)
);

create table users (
    id bigserial,
    username varchar(50),
    password varchar(256),
    role_id bigint references roles (id),
    primary key (id)
);

create table authors (
    id bigserial,
    full_name varchar(255),
    primary key (id)
);

create table genres (
    id bigserial,
    name varchar(255),
    primary key (id)
);

create table books (
    id bigserial,
    title varchar(255),
    author_id bigint references authors (id) on delete cascade,
    genre_id bigint references genres(id) on delete cascade,
    primary key (id)
);

create table comments (
    id bigserial,
    text varchar(1000),
    book_id bigint references books (id) on delete cascade,
    primary key (id)
);