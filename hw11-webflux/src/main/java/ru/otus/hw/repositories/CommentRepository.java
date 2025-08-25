package ru.otus.hw.repositories;

import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.otus.hw.models.Comment;


public interface CommentRepository extends ReactiveCrudRepository<Comment, Long> {

    @Query("""
            select c.id, c."text" , b.id as book_id, b.title as book_title
            from "comments" c
            join books b on b.id = c.book_id
            where c.book_id = :book_id
           """)
    Flux<Comment> findByBookId(@Param("book_id")long bookId);
}
