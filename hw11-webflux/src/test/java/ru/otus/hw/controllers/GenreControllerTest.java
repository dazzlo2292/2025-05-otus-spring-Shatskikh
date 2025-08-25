package ru.otus.hw.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.GenreRepository;
import ru.otus.hw.rest.controllers.GenreController;
import ru.otus.hw.rest.dto.GenreDto;
import ru.otus.hw.services.GenreServiceImpl;

import static org.mockito.Mockito.when;


@WebFluxTest(GenreController.class)
@Import({GenreServiceImpl.class})
public class GenreControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private GenreRepository genreRepository;

    @Test
    void shouldGetAllGenres() {
        when(genreRepository.findAll())
                .thenReturn(Flux.just(new Genre(1L, "Test_Genre_1")));

        webTestClient.get().uri("/api/v1/genre")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(GenreDto.class)
                .hasSize(1);
    }
}
