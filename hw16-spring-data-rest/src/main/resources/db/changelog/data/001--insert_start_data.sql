insert into roles (name)
values ('USER'), ('ADMIN');

insert into users(username, password, role_id)
values
('user','$2a$12$FFwZMqWgDjsfgWZr57BhXODCCiKKYpxHVE0BTCX80GlpQGMvtyMTO', 1),
('admin','$2a$12$L5F/FEg7JLj8qWQigfh6eesDeFZhlKc0t1AUU/v7JsNJWchcTiQym', 2);

insert into authors(full_name)
values ('Author_1'), ('Author_2'), ('Author_3');

insert into genres(name)
values ('Genre_1'), ('Genre_2'), ('Genre_3');

insert into books(title, author_id, genre_id)
values ('BookTitle_1', 1, 1), ('BookTitle_2', 2, 2), ('BookTitle_3', 3, 3);

insert into comments(text, book_id)
values ('Comment_1', 1), ('Comment_2', 1), ('Comment_1', 2);