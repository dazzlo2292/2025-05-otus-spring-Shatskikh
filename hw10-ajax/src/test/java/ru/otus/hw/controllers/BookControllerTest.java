package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.rest.controllers.BookController;
import ru.otus.hw.rest.dto.AuthorDto;
import ru.otus.hw.rest.dto.BookDto;
import ru.otus.hw.rest.dto.GenreDto;
import ru.otus.hw.models.Book;
import ru.otus.hw.rest.exceptions.ErrorDto;
import ru.otus.hw.services.BookService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;


    AuthorDto author = new AuthorDto(1L, "Test_Author_1");
    GenreDto genre = new GenreDto(1L, "Test_Genre_1");
    List<BookDto> books = List.of(
            new BookDto(1L, "Test_Book_1", author, genre),
            new BookDto(2L, "Test_Book_2", author, genre)
    );

    private final static ErrorDto ERROR = new ErrorDto("error", "Book not found!");

    private final static long BOOK_ID = 1L;


    @Test
    void shouldReturnCorrectABooksList() throws Exception {
        when(bookService.findAll()).thenReturn(books);

        mvc.perform(get("/api/v1/book"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(books)));
    }

    @Test
    void shouldReturnCorrectBook() throws Exception {
        when(bookService.findById(BOOK_ID)).thenReturn(Optional.ofNullable(books.get(0)));

        String url = "/api/v1/book/" + BOOK_ID;

        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(books.get(0))));
    }

    @Test
    void shouldReturnExpectedErrorWhenBookNotFound() throws Exception {
        when(bookService.findById(BOOK_ID)).thenReturn(Optional.empty());

        String url = "/api/v1/book/" + BOOK_ID;

        mvc.perform(get(url))
                .andExpect(status().isNotFound())
                .andExpect(content().json(mapper.writeValueAsString(ERROR)));
    }

    @Test
    void shouldCorrectSaveBook() throws Exception {
        Book book = new Book(1L, "Test_NewBook_1", author.toDomainObject(), genre.toDomainObject());
        when(bookService.save(any())).thenReturn(book);

        String expectedBook = mapper.writeValueAsString(BookDto.fromDomainObject(book));

        mvc.perform(post("/api/v1/book").contentType(APPLICATION_JSON)
                        .content(expectedBook))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedBook));
    }

    @Test
    void shouldCorrectDeleteBook() throws Exception {
        String url = "/api/v1/book/" + BOOK_ID;

        mvc.perform(delete(url))
                .andExpect(status().isOk());
    }
}
