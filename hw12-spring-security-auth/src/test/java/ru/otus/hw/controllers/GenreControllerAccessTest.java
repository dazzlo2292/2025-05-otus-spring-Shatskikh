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
import ru.otus.hw.services.GenreService;

import java.util.stream.Stream;

import static java.util.Objects.nonNull;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
@Import({SecurityConfiguration.class})
public class GenreControllerAccessTest {

    @Autowired
    private MockMvc mockMvc;

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
        var roles = new String[] {"DEFAULT"};
        return Stream.of(
                Arguments.of("get", "/genres", "user", roles, 200),
                Arguments.of("get", "/genres", null, null, 302)
        );
    }
}
