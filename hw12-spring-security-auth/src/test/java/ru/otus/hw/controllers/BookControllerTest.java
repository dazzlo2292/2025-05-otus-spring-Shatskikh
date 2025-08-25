package ru.otus.hw.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.auth.security.SecurityConfiguration;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@Import(SecurityConfiguration.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;


    @WithMockUser(username = "user", roles = {"DEFAULT"})
    @Test
    public void testGetAllBooksOnUser() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", roles = {"DEFAULT"})
    @Test
    public void testGetEditBookPageOnUser() throws Exception {
        mvc.perform(get("/edit/book"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", roles = {"DEFAULT"})
    @Test
    public void testGetAddBookPageOnUser() throws Exception {
        mvc.perform(get("/add/book"))
                .andExpect(status().isOk());
    }

    @WithMockUser(username = "user", roles = {"DEFAULT"})
    @Test
    public void testDeleteBookOnUser() throws Exception {
        mvc.perform(get("/delete/book"))
                .andExpect(status().isOk());
    }
}
