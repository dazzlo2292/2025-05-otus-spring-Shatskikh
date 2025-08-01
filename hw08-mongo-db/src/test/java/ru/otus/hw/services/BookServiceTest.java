package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.models.Book;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;


@DisplayName("Сервис для работы с книгами ")
@DataMongoTest
@Import({BookServiceImpl.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String BOOK_ID = "1";
    private static final String BOOK_ID_FOR_INSERT = "Test-1";
    private static final String BOOK_ID_FOR_DELETE = "2";

    @DisplayName("должен загружать все книги")
    @Test
    void shouldReturnAllBooks() {
        List<Book> expectedBooks = mongoTemplate.find(new Query(), Book.class, "books");
        List<Book> actualBooks = bookService.findAll();
        assertThat(actualBooks.size()).isEqualTo(expectedBooks.size());
    }

    @DisplayName("должен загружать книгу по id")
    @Test
    void shouldReturnBookById() {
        Query query = new Query(Criteria.where("_id").is(BOOK_ID));
        Book expectedBook = mongoTemplate.findOne(query, Book.class, "books");

        Optional<Book> actualBook = bookService.findById(BOOK_ID);

        assertThat(actualBook).isPresent().get().isEqualTo(expectedBook);
    }

    @DisplayName("должен добавлять книгу")
    @Test
    void shouldInsertBook() {
        Book expectedBook = bookService.insert(BOOK_ID_FOR_INSERT, "NewBook", "1", "1");

        Query query = new Query(Criteria.where("_id").is(BOOK_ID_FOR_INSERT));
        Book actualBook = mongoTemplate.findOne(query, Book.class, "books");

        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName("должен изменять книгу")
    @Test
    void shouldUpdateBook() {
        Book expectedBook = bookService.update(BOOK_ID, "editedBook", "1", "1");

        Query query = new Query(Criteria.where("_id").is(BOOK_ID));
        Book actualBook = mongoTemplate.findOne(query, Book.class, "books");

        assertThat(actualBook).isEqualTo(expectedBook);
    }

    @DisplayName("должен удалять книгу по id")
    @Test
    void shouldDeleteBook() {
        Query queryBeforeDelete = new Query(Criteria.where("_id").is(BOOK_ID_FOR_DELETE));
        Book bookBeforeDelete = mongoTemplate.findOne(queryBeforeDelete, Book.class, "books");
        assertThat(bookBeforeDelete).isNotNull();

        bookService.deleteById(BOOK_ID_FOR_DELETE);

        Query queryAfterDelete = new Query(Criteria.where("_id").is(BOOK_ID_FOR_DELETE));
        Book bookAfterDelete = mongoTemplate.findOne(queryAfterDelete, Book.class, "books");
        assertThat(bookAfterDelete).isNull();
    }

    @DisplayName("должен загружать все связи книги при запросе по id")
    @Test
    void shouldNotThrowLazyExceptionForFindBookById() {
        assertDoesNotThrow(() -> bookService
                .findById(BOOK_ID)
                .get()
                .getAuthor().getFullName());

        assertDoesNotThrow(() -> bookService
                .findById(BOOK_ID)
                .get()
                .getGenre().getName());
    }

    @DisplayName("должен загружать все связи книги при запросе всех книг")
    @Test
    void shouldNotThrowLazyExceptionForFindAllBooks() {
        assertDoesNotThrow(() -> bookService
                .findAll()
                .get(0)
                .getAuthor()
                .getFullName());

        assertDoesNotThrow(() -> bookService
                .findAll()
                .get(0)
                .getGenre()
                .getName());
    }
}
