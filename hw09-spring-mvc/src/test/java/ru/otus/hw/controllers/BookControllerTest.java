package ru.otus.hw.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;


    Author author = new Author(1L, "Test_Author_1");
    Genre genre = new Genre(1L, "Test_Genre_1");
    List<Book> books = List.of(
            new Book(1L, "Test_Book_1", author, genre),
            new Book(2L, "Test_Book_2", author, genre)
    );

    @Test
    void shouldRenderListPageWithCorrectViewAndModelAttributes() throws Exception {
        when(bookService.findAll()).thenReturn(books);

        List<BookDto> expectedBooks = books.stream()
                .map(BookDto::fromDomainObject).toList();

        mvc.perform(get("/"))
                .andExpect(view().name("books"))
                .andExpect(model().attribute("books", expectedBooks));
    }

    @Test
    void shouldRenderEditPageWithCorrectViewAndModelAttributes() throws Exception {
        when(bookService.findById(1L)).thenReturn(Optional.of(books.get(0)));
        when(authorService.findAll()).thenReturn(List.of(author));
        when(genreService.findAll()).thenReturn(List.of(genre));

        BookDto expectedBook = BookDto.fromDomainObject(books.get(0));
        List<AuthorDto> expectedAuthors = List.of(AuthorDto.fromDomainObject(author));
        List<GenreDto> expectedGenres = List.of(GenreDto.fromDomainObject(genre));

        mvc.perform(get("/edit/book").param("id", "1"))
                .andExpect(view().name("edit_book"))
                .andExpect(model().attribute("book", expectedBook))
                .andExpect(model().attribute("authors", expectedAuthors))
                .andExpect(model().attribute("genres", expectedGenres));
    }

    @Test
    void shouldRenderAddPageWithCorrectViewAndModelAttributes() throws Exception {
        when(bookService.findAll()).thenReturn(books);
        when(authorService.findAll()).thenReturn(List.of(author));
        when(genreService.findAll()).thenReturn(List.of(genre));

        BookDto expectedBook = BookDto.fromDomainObject(new Book(3L, null, null, null));
        List<AuthorDto> expectedAuthors = List.of(AuthorDto.fromDomainObject(author));
        List<GenreDto> expectedGenres = List.of(GenreDto.fromDomainObject(genre));

        mvc.perform(get("/add/book"))
                .andExpect(view().name("add_book"))
                .andExpect(model().attribute("book", expectedBook))
                .andExpect(model().attribute("authors", expectedAuthors))
                .andExpect(model().attribute("genres", expectedGenres));
    }

    @Test
    void shouldRenderErrorPageWhenBookNotFound() throws Exception {
        when(bookService.findById(1L)).thenThrow(new EntityNotFoundException("Book not found"));

        mvc.perform(get("/edit_book").param("id", "1"))
                .andExpect(view().name("error"));
    }

    @Test
    void shouldNotSaveBookAndRedirectToCurrentPageWithErrors() throws Exception {
        mvc.perform(post("/edit/book")
                        .param("id", "3")
                        .param("title", "NewTitle"))
                .andExpect(view().name("edit_book"));
    }

    @Test
    void shouldDeleteBookAndRedirectToStartPage() throws Exception {
        mvc.perform(delete("/delete/book").param("id", "1"))
                .andExpect(view().name("redirect:/"));
        verify(bookService, times(1)).deleteById(any(Long.class));
    }
}
