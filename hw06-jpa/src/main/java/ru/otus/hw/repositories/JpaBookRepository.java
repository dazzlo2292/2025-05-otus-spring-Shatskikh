package ru.otus.hw.repositories;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

import static org.springframework.data.jpa.repository.EntityGraph.EntityGraphType.FETCH;

@Repository
public class JpaBookRepository implements BookRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    public JpaBookRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Optional<Book> findById(long id) {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("book-author-genre-entity-graph");

        TypedQuery<Book> query = entityManager.createQuery(
                """
                        select b from Book b
                        where b.id = :id
                   """, Book.class
        );

        query.setParameter("id", id);
        query.setHint(FETCH.getKey(), entityGraph);

        return Optional.ofNullable(query.getSingleResult());
    }

    @Override
    public List<Book> findAll() {
        EntityGraph<?> entityGraph = entityManager.getEntityGraph("book-author-genre-entity-graph");

        TypedQuery<Book> query = entityManager.createQuery(
                """
                        select b from Book b
                   """, Book.class
        );

        query.setHint(FETCH.getKey(), entityGraph);

        return query.getResultList();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == 0) {
            entityManager.persist(book);
            return book;
        }
        return entityManager.merge(book);
    }

    @Override
    public void deleteById(long id) {
        Book targetBook = entityManager.find(Book.class, id);
        entityManager.remove(targetBook);
    }
}
