package ru.otus.hw.repositories;

import io.r2dbc.spi.Readable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.BookInfo;
import ru.otus.hw.models.Genre;

@Repository
public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    private static final String Q_BOOKS_WITH_AUTHOR_AND_GENRE = """
            select 
            b.*, a.full_name , g."name" 
                from books b
                inner join authors a on a.id = b.author_id
                inner join genres g on g.id = b.genre_id;
            """;

    private static final String Q_BOOK_WITH_AUTHOR_AND_GENRE = """
            select 
            b.id, b.title, b.author_id , a.full_name , b.genre_id , g."name"
                from books b
                inner join authors a on a.id = b.author_id
                inner join genres g on g.id = b.genre_id
            where b.id = $1;
            """;

    private final R2dbcEntityTemplate template;

    public BookRepositoryCustomImpl(R2dbcEntityTemplate template) {
        this.template = template;
    }

    @Override
    public Mono<BookInfo> findById(long id) {
        return template.getDatabaseClient().inConnection(connection ->
                Mono.from(connection.createStatement(Q_BOOK_WITH_AUTHOR_AND_GENRE)
                                .bind(0, id)
                                .execute())
                        .flatMap(result -> Mono.from(result.map(this::mapper))));
    }

    @Override
    public Flux<BookInfo> findAllBooksWithAuthorAndGenre() {
        return template.getDatabaseClient().inConnectionMany(connection ->
                Flux.from(connection.createStatement(Q_BOOKS_WITH_AUTHOR_AND_GENRE)
                                .execute())
                        .flatMap(result -> result.map(this::mapper)));
    }

    private BookInfo mapper(Readable selectedRecord) {
        return new BookInfo(
                selectedRecord.get("id", Long.class),
                selectedRecord.get("title", String.class),
                new Author(
                        selectedRecord.get("author_id", Long.class),
                        selectedRecord.get("full_name", String.class)),
                new Genre(
                        selectedRecord.get("genre_id", Long.class),
                        selectedRecord.get("name", String.class))
        );
    }
}
