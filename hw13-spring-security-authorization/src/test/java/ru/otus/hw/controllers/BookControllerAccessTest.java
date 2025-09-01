package ru.otus.hw.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.hw.auth.security.SecurityConfiguration;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.stream.Stream;

import static java.util.Objects.nonNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
@Import(SecurityConfiguration.class)
public class BookControllerAccessTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;


    @DisplayName("Should return expected status")
    @ParameterizedTest(name = "{0} {1} for user ({2}) should return {4} status")
    @MethodSource("getTestData")
    void shouldReturnExpectedStatus(String method, String url, String userName, String[] roles, int status) throws Exception {
        var request = RequestBuilderUtils.method2RequestBuilder(method, url);

        if (nonNull(userName)) {
            request = request.with(user(userName).roles(roles));
        }

        mockMvc.perform(request)
                .andExpect(status().is(status));
    }

    public static Stream<Arguments> getTestData() {
        var userRoles = new String[] {"USER"};
        var adminRoles = new String[] {"ADMIN"};
        return Stream.of(
                Arguments.of("get", "/", "user", userRoles, 200),
                Arguments.of("get", "/", null, null, 401),
                Arguments.of("get", "/edit/book", "user", userRoles, 403),
                Arguments.of("get", "/edit/book", "admin", adminRoles, 200),
                Arguments.of("get", "/edit/book", null, null, 401),
                Arguments.of("post", "/edit/book", "user", userRoles, 403),
                Arguments.of("post", "/edit/book", "admin", adminRoles, 200),
                Arguments.of("post", "/edit/book", null, null, 401),
                Arguments.of("get", "/add/book", "user", userRoles, 403),
                Arguments.of("get", "/add/book", "admin", adminRoles, 200),
                Arguments.of("get", "/add/book", null, null, 401),
                Arguments.of("delete", "/delete/book", "user", userRoles, 403),
                Arguments.of("delete", "/delete/book", "admin", adminRoles, 200),
                Arguments.of("delete", "/delete/book", null, null, 401)
        );
    }
}
