package ru.otus.hw.repositories;

import lombok.NonNull;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.models.Book;


public interface BookRepository extends ReactiveCrudRepository<Book, Long> {
    @Query("""
            select b.id, b.title, b.author_id , a.full_name as author_name , b.genre_id , g."name" as genre_name
            from books b
            join authors a on a.id = b.author_id
            join genres g on g.id = b.genre_id
            where b.id = :id
           """)
    Mono<Book> findById(@Param("id") long id);

    @NonNull
    Flux<Book> findAll();
}
