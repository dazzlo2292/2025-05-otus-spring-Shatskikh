package ru.otus.hw.services;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("Сервис для работы с комментариями ")
@DataJpaTest
@Import({CommentServiceImpl.class})
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    private static final long COMMENT_ID = 1L;
    private static final long BOOK_ID = 1L;

    @DisplayName("должен загружать все связи комментария при запросе по id")
    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void shouldNotThrowLazyExceptionForFindCommentById() {
        assertDoesNotThrow(() -> commentService
                .findById(COMMENT_ID)
                .get()
                .getBook()
                .getTitle());
    }

    @DisplayName("должен загружать все связи комментариев при запросе по id книги")
    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void shouldNotThrowLazyExceptionForFindCommentsByBookId() {
        assertDoesNotThrow(() -> commentService
                .findAllByBookId(BOOK_ID)
                .get(0)
                .getBook()
                .getAuthor());

        assertDoesNotThrow(() -> commentService
                .findAllByBookId(BOOK_ID)
                .get(0)
                .getBook()
                .getGenre());
    }
}
