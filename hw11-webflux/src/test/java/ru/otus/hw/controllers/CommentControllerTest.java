package ru.otus.hw.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.CommentRepository;
import ru.otus.hw.rest.controllers.CommentController;
import ru.otus.hw.rest.dto.CommentDto;
import ru.otus.hw.services.CommentServiceImpl;

import static org.mockito.Mockito.when;


@WebFluxTest(CommentController.class)
@Import({CommentServiceImpl.class})
public class CommentControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private CommentRepository commentRepository;

    private static final Long BOOK_ID = 1L;

    @Test
    void shouldGetAllCommentsByBookId() {
        when(commentRepository.findByBookId(BOOK_ID))
                .thenReturn(Flux.just(new Comment(1L, "Test_Comment_Text_1", 1L, "Test_Book_1")));

        webTestClient.get().uri("/api/v1/book/1/comment")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CommentDto.class)
                .hasSize(1);
    }
}
