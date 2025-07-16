package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.hw.models.Genre;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тестирование репозитория для работы с жанрами")
@DataJpaTest
@Import(JpaGenreRepository.class)
public class JpaGenreRepositoryTest {

    @Autowired
    private JpaGenreRepository genreRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private static final long GENRE_ID = 1L;
    private static final int GENRES_SIZE = 3;

    @DisplayName("Получение жанра по id")
    @Test
    void shouldFindGenreById() {
        Optional<Genre> actualGenre = genreRepository.findById(GENRE_ID);
        Genre expectedGenre = testEntityManager.find(Genre.class, GENRE_ID);
        assertThat(actualGenre).isPresent()
                .get().isEqualTo(expectedGenre);
    }

    @DisplayName("Получение списка всех жанров")
    @Test
    void shouldReturnCorrectGenresList() {
        List<Genre> actualGenres = genreRepository.findAll();
        assertThat(actualGenres).hasSize(GENRES_SIZE);
    }
}
