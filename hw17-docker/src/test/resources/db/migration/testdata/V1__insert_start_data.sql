insert into users(username, password)
values
('user','$2a$12$FFwZMqWgDjsfgWZr57BhXODCCiKKYpxHVE0BTCX80GlpQGMvtyMTO'),
('admin','$2a$12$L5F/FEg7JLj8qWQigfh6eesDeFZhlKc0t1AUU/v7JsNJWchcTiQym');

insert into authors (full_name)
values ('Test_Author_1'), ('Test_Author_2'), ('Test_Author_3');

insert into genres (name)
values ('Test_Genre_1'), ('Test_Genre_2'), ('Test_Genre_3');

insert into books (title, author_id, genre_id)
values ('Test_BookTitle_1', 1, 1), ('Test_BookTitle_2', 2, 2), ('Test_BookTitle_3', 3, 3);

insert into comments (text, book_id)
values ('Test_Comment_1', 1), ('Test_Comment_2', 1), ('Test_Comment_3', 2)