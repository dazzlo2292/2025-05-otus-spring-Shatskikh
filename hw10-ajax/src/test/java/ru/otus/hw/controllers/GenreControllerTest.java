package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.rest.controllers.GenreController;
import ru.otus.hw.rest.dto.GenreDto;
import ru.otus.hw.rest.exceptions.ErrorDto;
import ru.otus.hw.services.GenreService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
public class GenreControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private GenreService genreService;


    List<GenreDto> genres = List.of(
            new GenreDto(1L, "Test_Genre_1"),
            new GenreDto(2L, "Test_Genre_2")
    );

    private final static ErrorDto ERROR = new ErrorDto("error", "Genres not found!");

    @Test
    void shouldReturnCorrectGenresList() throws Exception {
        when(genreService.findAll()).thenReturn(genres);

        mvc.perform(get("/api/v1/genre"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(genres)));
    }

    @Test
    void shouldReturnExpectedErrorWhenGenresNotFound() throws Exception {
        when(genreService.findAll()).thenReturn(List.of());

        mvc.perform(get("/api/v1/genre"))
                .andExpect(status().isNotFound())
                .andExpect(content().json(mapper.writeValueAsString(ERROR)));
    }
}
