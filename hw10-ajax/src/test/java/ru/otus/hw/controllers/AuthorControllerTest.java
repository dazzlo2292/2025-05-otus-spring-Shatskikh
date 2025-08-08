package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.rest.controllers.AuthorController;
import ru.otus.hw.rest.dto.AuthorDto;
import ru.otus.hw.rest.exceptions.ErrorDto;
import ru.otus.hw.services.AuthorService;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(AuthorController.class)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AuthorService authorService;


    List<AuthorDto> authors = List.of(
            new AuthorDto(1L, "Test_Author_1"),
            new AuthorDto(2L, "Test_Author_2")
    );

    private final static ErrorDto ERROR = new ErrorDto("error", "Authors not found!");

    @Test
    void shouldReturnCorrectAuthorsList() throws Exception {
        when(authorService.findAll()).thenReturn(authors);

        mvc.perform(get("/api/v1/author"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(authors)));
    }

    @Test
    void shouldReturnExpectedErrorWhenAuthorsNotFound() throws Exception {
        when(authorService.findAll()).thenReturn(List.of());

        mvc.perform(get("/api/v1/author"))
                .andExpect(status().isNotFound())
                .andExpect(content().json(mapper.writeValueAsString(ERROR)));
    }
}
