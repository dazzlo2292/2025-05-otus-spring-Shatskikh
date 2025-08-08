package ru.otus.hw.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.rest.controllers.CommentController;
import ru.otus.hw.rest.dto.AuthorDto;
import ru.otus.hw.rest.dto.BookDto;
import ru.otus.hw.rest.dto.CommentDto;
import ru.otus.hw.rest.dto.GenreDto;
import ru.otus.hw.services.CommentService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
public class CommentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CommentService commentService;

    private static final long BOOK_ID = 1;

    AuthorDto author = new AuthorDto(1L, "Test_Author_1");
    GenreDto genre = new GenreDto(1L, "Test_Genre_1");
    BookDto book = new BookDto(1L, "Test_Book_1", author, genre);

    List<CommentDto> comments = List.of(
            new CommentDto(1L, "Test_Comment_1", book),
            new CommentDto(2L, "Test_Comment_2", book)
    );

    @Test
    void shouldReturnCorrectCommentsList() throws Exception {
        String url = "/api/v1/book/" + BOOK_ID + "/comment";

        when(commentService.findAllByBookId(BOOK_ID)).thenReturn(comments);

        mvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(comments)));
    }
}
