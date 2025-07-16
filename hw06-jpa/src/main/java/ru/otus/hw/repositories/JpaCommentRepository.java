package ru.otus.hw.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Repository
public class JpaCommentRepository implements CommentRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Optional<Comment> findById(long id) {
        TypedQuery<Comment> query = entityManager.createQuery(
                """
                        select c from Comment c
                        left join fetch c.book b
                        left join fetch b.author
                        left join fetch b.genre
                        where c.id = :id
                   """, Comment.class);

        query.setParameter("id", id);

        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<Comment> findAllByBookId(long id) {
        TypedQuery<Comment> query = entityManager.createQuery(
                """
                        select c from Comment c
                        left join fetch c.book b
                        left join fetch b.author
                        left join fetch b.genre
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
