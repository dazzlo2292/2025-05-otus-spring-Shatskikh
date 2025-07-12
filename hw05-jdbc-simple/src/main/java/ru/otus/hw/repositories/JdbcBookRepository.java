package ru.otus.hw.repositories;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class JdbcBookRepository implements BookRepository {

    private final NamedParameterJdbcTemplate jdbc;

    public JdbcBookRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.jdbc = namedParameterJdbcTemplate;
    }

    @Override
    public Optional<Book> findById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        List<Book> books = jdbc.query(
                """
                        select b.id, b.title, b.author_id, a.full_name as author_name, b.genre_id, g.name as genre_name
                        from books b
                        join authors a on a.id = b.author_id
                        join genres g on g.id = b.genre_id
                        where b.id = :id;
                    """,
                params, new BookRowMapper()
        );
        if (books.size() == 1) {
            return Optional.of(books.get(0));
        }
        return Optional.empty();
    }

    @Override
    public List<Book> findAll() {
        return jdbc.query(
                """
                        select b.id, b.title, b.author_id, a.full_name as author_name, b.genre_id, g.name as genre_name
                        from books b
                        inner join authors a on a.id = b.author_id
                        inner join genres g on g.id = b.genre_id;
                    """,
                new BookRowMapper());
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            return insert(book);
        }
        return update(book);
    }

    @Override
    public void deleteById(long id) {
        Map<String, Object> params = Collections.singletonMap("id", id);
        jdbc.update(
                "delete from books where id = :id;", params
        );
    }

    private Book insert(Book book) {
        var keyHolder = new GeneratedKeyHolder();

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("title", book.getTitle());
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("genre_id", book.getGenre().getId());

        jdbc.update("""
                            insert into books (title, author_id, genre_id)
                            values (:title, :author_id, :genre_id);
                        """, params, keyHolder, new String[]{"id"});

        //noinspection DataFlowIssue
        book.setId(keyHolder.getKeyAs(Long.class));
        return book;
    }

    private Book update(Book book) {
        if (findById(book.getId()).isEmpty()) {
            throw new EntityNotFoundException("Book with id %d not found".formatted(book.getId()));
        }

        MapSqlParameterSource params = new MapSqlParameterSource();

        params.addValue("id", book.getId());
        params.addValue("title", book.getTitle());
        params.addValue("author_id", book.getAuthor().getId());
        params.addValue("genre_id", book.getGenre().getId());

        jdbc.update("""
                            update books set title = :title, author_id = :author_id, genre_id = :genre_id
                            where id = :id
                        """, params);

        return book;
    }

    private static class BookRowMapper implements RowMapper<Book> {

        @Override
        public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
            long id = rs.getLong("id");
            String title = rs.getString("title");

            long authorId = rs.getLong("author_id");
            String authorName = rs.getString("author_name");
            Author author = new Author(authorId, authorName);

            long genreId = rs.getLong("genre_id");
            String genreName = rs.getString("genre_name");
            Genre genre = new Genre(genreId, genreName);

            return new Book(id, title, author, genre);
        }
    }
}
