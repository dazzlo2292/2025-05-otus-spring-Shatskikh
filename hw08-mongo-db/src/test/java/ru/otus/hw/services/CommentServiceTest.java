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
import ru.otus.hw.converters.CommentConverter;
import ru.otus.hw.models.Comment;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Сервис для работы с комментариями ")
@DataMongoTest
@Import({CommentServiceImpl.class, CommentConverter.class})
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class CommentServiceTest {

    @Autowired
    private CommentService commentService;

    @Autowired
    private MongoTemplate mongoTemplate;

    private static final String COMMENT_ID = "1";
    private static final String COMMENT_ID_FOR_INSERT = "Test-1";
    private static final String COMMENT_ID_FOR_DELETE = "2";
    private static final String BOOK_ID = "1";

    @DisplayName("должен загружать комментарий по id")
    @Test
    void shouldReturnCommentById() {
        Query query = new Query(Criteria.where("_id").is(COMMENT_ID));
        Comment expectedComment = mongoTemplate.findOne(query, Comment.class, "comments");

        Optional<Comment> actualComment = commentService.findById(COMMENT_ID);

        assertThat(actualComment).isPresent().get().isEqualTo(expectedComment);
    }

    @DisplayName("должен загружать все комментарии по id книги")
    @Test
    void shouldReturnAllCommentsByBookId() {
        Query query = new Query(Criteria.where("book").is(BOOK_ID));
        List<Comment> expectedComments = mongoTemplate.find(query, Comment.class, "comments");

        List<Comment> actualComments = commentService.findAllByBookId(BOOK_ID);
        assertThat(actualComments.size()).isEqualTo(expectedComments.size());
    }

    @DisplayName("должен добавлять комментарий")
    @Test
    void shouldInsertComment() {
        Comment expectedComment = commentService.insert(COMMENT_ID_FOR_INSERT, "NewComment", "1");

        Query query = new Query(Criteria.where("_id").is(COMMENT_ID_FOR_INSERT));
        Comment actualComment = mongoTemplate.findOne(query, Comment.class, "comments");

        assertThat(actualComment).isEqualTo(expectedComment);
    }

    @DisplayName("должен изменять комментарий")
    @Test
    void shouldUpdateComment() {
        Comment expectedComment = commentService.update(COMMENT_ID, "editedComment", "1");

        Query query = new Query(Criteria.where("_id").is(COMMENT_ID));
        Comment actualComment = mongoTemplate.findOne(query, Comment.class, "comments");

        assertThat(actualComment).isEqualTo(expectedComment);
    }

    @DisplayName("должен удалять комментарий по id")
    @Test
    void shouldDeleteComment() {
        Query queryBeforeDelete = new Query(Criteria.where("_id").is(COMMENT_ID_FOR_DELETE));
        Comment commentBeforeDelete = mongoTemplate.findOne(queryBeforeDelete, Comment.class, "comments");
        assertThat(commentBeforeDelete).isNotNull();

        commentService.deleteById(COMMENT_ID_FOR_DELETE);

        Query queryAfterDelete = new Query(Criteria.where("_id").is(COMMENT_ID_FOR_DELETE));
        Comment commentAfterDelete = mongoTemplate.findOne(queryAfterDelete, Comment.class, "comments");
        assertThat(commentAfterDelete).isNull();
    }
}
