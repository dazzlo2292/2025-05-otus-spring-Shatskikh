package ru.otus.hw.repositories;

import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("Репозиторий для работы с книгами ")
@DataJpaTest
@Import({JpaBookRepository.class})
class JpaBookRepositoryTest {

    @Autowired
    private JpaBookRepository bookRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private static final long BOOK_ID = 1L;
    private static final int BOOKS_SIZE = 3;
    private static final String OLD_BOOK_TITLE = "Test_BookTitle_1";
    private static final String NEW_BOOK_TITLE = "New_Test_BookTitle_1";

    @DisplayName("должен загружать книгу по id")
    @Test
    void shouldFindBookById() {
        Optional<Book> actualBook = bookRepository.findById(BOOK_ID);
        Book expectedBook = testEntityManager.find(Book.class, BOOK_ID);

        assertThat(actualBook).isPresent()
                .get()
                .isEqualTo(expectedBook);
    }

    @DisplayName("должен загружать все связи книги при запросе по id")
    @Test
    void shouldNotThrowLazyExceptionForFindBookById() {
        assertDoesNotThrow(() -> bookRepository
                .findById(BOOK_ID)
                .get()
                .getAuthor());

        assertDoesNotThrow(() -> bookRepository
                .findById(BOOK_ID)
                .get()
                .getGenre());

        assertDoesNotThrow(() -> bookRepository
                .findById(BOOK_ID)
                .get()
                .getComments());
    }

    @DisplayName("должен загружать список всех книг")
    @Test
    void shouldFindAllBooksList() {
        List<Book> actualBooks = bookRepository.findAll();
        assertThat(actualBooks).hasSize(BOOKS_SIZE);
    }

    @DisplayName("должен сохранять новую книгу")
    @Test
    void shouldSaveNewBook() {
        Author author = testEntityManager.find(Author.class, 1);
        Genre genre = testEntityManager.find(Genre.class, 1);

        Book bookBeforeSave = testEntityManager.find(Book.class, 4);
        assertThat(bookBeforeSave).isNull();

        bookRepository.save(new Book(0, "Test_New_Book_1", author, genre));

        Book bookAfterSave = testEntityManager.find(Book.class, 4);
        assertThat(bookAfterSave).isNotNull();
    }

    @DisplayName("должен сохранять измененную книгу")
    @Test
    void shouldSaveUpdatedBook() {
        Book bookBeforeUpdate = testEntityManager.find(Book.class, BOOK_ID);
        assertThat(bookBeforeUpdate).hasFieldOrPropertyWithValue("title", OLD_BOOK_TITLE);

        bookBeforeUpdate.setTitle(NEW_BOOK_TITLE);
        bookRepository.save(bookBeforeUpdate);

        Book bookAfterSave = testEntityManager.find(Book.class, BOOK_ID);
        assertThat(bookAfterSave).hasFieldOrPropertyWithValue("title", NEW_BOOK_TITLE);
    }

    @DisplayName("должен удалять книгу по id ")
    @Test
    void shouldDeleteBook() {
        Book targetBook = testEntityManager.find(Book.class, BOOK_ID);
        assertThat(targetBook).isNotNull();

        bookRepository.deleteById(BOOK_ID);

        Book deletedBook = testEntityManager.find(Book.class, BOOK_ID);
        assertThat(deletedBook).isNull();
    }
}