package ru.otus.hw.repositories;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @EntityGraph(value = "comment-book-entity-graph")
    Optional<Comment> findById(long id);

    @EntityGraph(value = "comment-book-entity-graph")
    List<Comment> findByBookId(long bookId);
}
