package ru.otus.hw.repositories;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;


@RequiredArgsConstructor
@Repository
public class JpaCommentRepository implements CommentRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Optional<Comment> findById(long id) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("comment-book-entity-graph");

        Map<String, Object> properties = new HashMap<>();
        properties.put(FETCH.getKey(), entityGraph);

        return Optional.ofNullable(entityManager.find(Comment.class, id, properties));
    }

    @Override
    public List<Comment> findAllByBookId(long id) {
        Book book = entityManager.find(Book.class, id);

        TypedQuery<Comment> query = entityManager.createQuery(
                """
                        select c from Comment c
                        left join c.book b
                        where b.id = :id
                   """, Comment.class);

        query.setParameter("id", id);

        return query.getResultList();
    }

    @Override
    public Comment save(Comment comment) {
        if (comment.getId() == 0) {
            entityManager.persist(comment);
            return comment;
        }
        return entityManager.merge(comment);
    }

    @Override
    public void deleteById(long id) {
        Comment targetComment = entityManager.find(Comment.class, id);
        entityManager.remove(targetComment);
    }
}
