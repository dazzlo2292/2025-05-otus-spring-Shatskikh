package ru.otus.hw.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.*;
import ru.otus.hw.rest.controllers.BookController;
import ru.otus.hw.rest.dto.BookProjectionDto;
import ru.otus.hw.models.BookProjection;
import ru.otus.hw.rest.dto.BookDto;
import ru.otus.hw.services.BookServiceImpl;


import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;


@WebFluxTest(BookController.class)
@Import({BookServiceImpl.class})
public class BookControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    @MockBean
    private BookRepository bookRepository;

    private static final long BOOK_ID = 1L;

    @Test
    void shouldGetAllBooksWithAuthorAndGenre() {
        when(bookRepository.findAllBooksWithAuthorAndGenre())
                .thenReturn(Flux.just(new Book(
                        1L,
                        "Test_Book_1",
                        new Author(1L, "Test_Author_1"),
                        new Genre(1L, "Test_Genre_1"))));

        webTestClient.get().uri("/api/v1/book")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookProjectionDto.class)
                .hasSize(1);
    }

    @Test
    void shouldGetBookById() {
        when(bookRepository.findById(BOOK_ID))
                .thenReturn(Mono.just(new Book(
                        1L,
                        "Test_Book_1",
                        new Author(1L, "Test_Author_1"),
                        new Genre(1L, "Test_Genre_1"))));

        webTestClient.get().uri("/api/v1/book/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookProjection.class)
                .value(book -> {
                    assertThat(book.getTitle()).isEqualTo("Test_Book_1");
                });
    }

    @Test
    void shouldSaveBook() {
        BookDto newBookInfoDto = new BookDto(
                1L,
                "Test_Book_1",
                new Author(1L, "Test_Author_1"),
                new Genre(1L, "Test_Genre_1"));
        BookProjection savedBook = new BookProjection(1L, "Test_Book_1", 1L, 1L);

        when(authorRepository.findById(1L)).thenReturn(Mono.just(new Author(1L, "Test_Author_1")));
        when(genreRepository.findById(1L)).thenReturn(Mono.just(new Genre(1L, "Test_Genre_1")));

        when(bookRepository.save(any(BookProjection.class)))
                .thenReturn(Mono.just(savedBook));

        webTestClient.post().uri("/api/v1/book")
                .bodyValue(newBookInfoDto)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookProjectionDto.class)
                .value(createdBook -> {
                    assertThat(createdBook.getId()).isEqualTo(1L);
                    assertThat(createdBook.getTitle()).isEqualTo("Test_Book_1");
                });
    }

    @Test
    void shoulDeleteBookById() {
        when(bookRepository.deleteById(BOOK_ID))
                .thenReturn(Mono.empty());

        webTestClient.delete().uri("/api/v1/book/1")
                .exchange()
                .expectStatus().isOk()
                .expectBody().isEmpty();
    }
}
