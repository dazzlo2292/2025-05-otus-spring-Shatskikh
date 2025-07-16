package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@DisplayName("Репозиторий для работы с комментариями ")
@DataJpaTest
@Import({JpaCommentRepository.class})
class JpaCommentRepositoryTest {

    @Autowired
    private JpaCommentRepository commentRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private static final long COMMENT_ID = 1L;
    private static final long BOOK_ID = 1;
    private static final String OLD_COMMENT_TEXT = "Test_Comment_1";
    private static final String NEW_COMMENT_TEXT = "New_Test_Comment_1";

    @DisplayName("должен загружать комментарий по id")
    @Test
    void shouldFindCommentById() {
        Optional<Comment> actualComment = commentRepository.findById(COMMENT_ID);
        Comment expectedComment = testEntityManager.find(Comment.class, COMMENT_ID);

        assertThat(actualComment).isPresent()
                .get()
                .isEqualTo(expectedComment);
    }

    @DisplayName("должен загружать все связи комментария при запросе по id")
    @Test
    void shouldNotThrowLazyExceptionForFindCommentById() {
        assertDoesNotThrow(() -> commentRepository
                .findById(COMMENT_ID)
                .get()
                .getBook());
    }

    @DisplayName("должен загружать все связи комментариев при запросе по id книги")
    @Test
    void shouldNotThrowLazyExceptionForFindCommentsByBookId() {
        assertDoesNotThrow(() -> commentRepository
                .findAllByBookId(BOOK_ID)
                .get(0)
                .getBook()
                .getAuthor());

        assertDoesNotThrow(() -> commentRepository
                .findAllByBookId(BOOK_ID)
                .get(0)
                .getBook()
                .getGenre());
    }

    @DisplayName("должен возвращать комментарии по id книги")
    @Test
    void shouldFindCommentsByBookId() {
        Book targetBook = testEntityManager.find(Book.class, BOOK_ID);
        List<Comment> actualComments = commentRepository.findAllByBookId(BOOK_ID);

        assertThat(targetBook.getComments().size()).isEqualTo(actualComments.size());
    }

    @DisplayName("должен сохранять новый комментарий")
    @Test
    void shouldSaveNewComment() {
        Book book = testEntityManager.find(Book.class, 1);

        Comment commentBeforeSave = testEntityManager.find(Comment.class, 4);
        assertThat(commentBeforeSave).isNull();

        commentRepository.save(new Comment(0, "Test_New_Comment_1", book));

        Comment commentAfterSave = testEntityManager.find(Comment.class, 4);
        assertThat(commentAfterSave).isNotNull();
    }

    @DisplayName("должен сохранять измененный комментарий")
    @Test
    void shouldSaveUpdatedComment() {
        Comment commentBeforeUpdate = testEntityManager.find(Comment.class, COMMENT_ID);
        assertThat(commentBeforeUpdate).hasFieldOrPropertyWithValue("text", OLD_COMMENT_TEXT);

        commentBeforeUpdate.setText(NEW_COMMENT_TEXT);
        commentRepository.save(commentBeforeUpdate);

        Comment commentAfterSave = testEntityManager.find(Comment.class, COMMENT_ID);
        assertThat(commentAfterSave).hasFieldOrPropertyWithValue("text", NEW_COMMENT_TEXT);
    }

    @DisplayName("должен удалять комментарий по id ")
    @Test
    void shouldDeleteComment() {
        Comment targetComment = testEntityManager.find(Comment.class, COMMENT_ID);
        assertThat(targetComment).isNotNull();

        commentRepository.deleteById(COMMENT_ID);

        Comment deletedComment = testEntityManager.find(Comment.class, COMMENT_ID);
        assertThat(deletedComment).isNull();
    }
}