package ru.otus.hw.repositories;

import io.r2dbc.spi.Readable;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import ru.otus.hw.rest.dto.BookDto;


@Repository
public class BookRepositoryCustom {

    private static final String Q_BOOKS_WITH_AUTHOR_AND_GENRE = """
            select 
            b.*, a.full_name , g."name" 
                from books b
                join authors a on a.id = b.author_id
                join genres g on g.id = b.genre_id;
            """;

    private final R2dbcEntityTemplate template;

    public BookRepositoryCustom(R2dbcEntityTemplate template) {
        this.template = template;
    }

    public Flux<BookDto> findAll() {
        return template.getDatabaseClient().inConnectionMany(connection ->
                Flux.from(connection.createStatement(Q_BOOKS_WITH_AUTHOR_AND_GENRE)
                                .execute())
                        .flatMap(result -> result.map(this::mapper)));
    }

    private BookDto mapper(Readable selectedRecord) {
        return new BookDto(
                selectedRecord.get("id", Long.class),
                selectedRecord.get("title", String.class),
                selectedRecord.get("author_id", Long.class),
                selectedRecord.get("full_name", String.class),
                selectedRecord.get("genre_id", Long.class),
                selectedRecord.get("name", String.class)
        );
    }
}
