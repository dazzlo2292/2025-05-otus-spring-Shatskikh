package ru.otus.hw.services;

import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.JpaBookRepository;
import ru.otus.hw.repositories.JpaCommentRepository;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("Сервис для работы с комментариями ")
@DataJpaTest
@Import({CommentServiceImpl.class, JpaBookRepository.class, JpaCommentRepository.class, CommentConverter.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private CommentConverter commentConverter;

    private static final long COMMENT_ID = 1L;
    private static final long BOOK_ID = 1L;

    @DisplayName("не должен бросать LazyInitializationException при использовании комментария, полученного по id")
    @Test
    void shouldNotThrowLazyExceptionForFindCommentById() {
        Optional<Comment> comment = commentService.findById(COMMENT_ID);

        assertDoesNotThrow(() -> commentConverter.commentToString(comment.get()));
    }

    @DisplayName("не должен бросать LazyInitializationException при использовании комментариев, полученных по id книги")
    @Test
    void shouldNotThrowLazyExceptionForFindCommentsByBookId() {
        List<Comment> comments = commentService.findAllByBookId(BOOK_ID);

        assertDoesNotThrow(() -> commentConverter.commentToString(comments.get(0)));
    }
}
