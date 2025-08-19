package ru.otus.hw.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.otus.hw.models.Author;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.rest.controllers.AuthorController;
import ru.otus.hw.rest.dto.AuthorDto;
import ru.otus.hw.services.AuthorServiceImpl;

import static org.mockito.Mockito.when;


@WebFluxTest(AuthorController.class)
@Import({AuthorServiceImpl.class})
public class AuthorControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AuthorRepository authorRepository;

    @Test
    void shouldGetAllAuthors() {
        when(authorRepository.findAll())
                .thenReturn(Flux.just(new Author(1L, "Test_Author_1")));

        webTestClient.get().uri("/api/v1/author")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AuthorDto.class)
                .hasSize(1);
    }
}
