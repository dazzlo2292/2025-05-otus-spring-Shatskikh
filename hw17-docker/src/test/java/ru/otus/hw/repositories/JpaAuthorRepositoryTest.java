package ru.otus.hw.repositories;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.hw.models.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий для работы с авторами")
@DataJpaTest
public class JpaAuthorRepositoryTest {

    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    private static final long AUTHOR_ID = 1L;
    private static final int AUTHORS_SIZE = 3;

    @DisplayName("Получение автора по id")
    @Test
    void shouldFindAuthorById() {
        Optional<Author> actualAuthor = authorRepository.findById(AUTHOR_ID);
        Author expectedAuthor = testEntityManager.find(Author.class, AUTHOR_ID);
        assertThat(actualAuthor).isPresent()
                .get().isEqualTo(expectedAuthor);
    }

    @DisplayName("Получение списка всех авторов")
    @Test
    void shouldReturnCorrectAuthorsList() {
        List<Author> actualAuthors = authorRepository.findAll();
        assertThat(actualAuthors).hasSize(AUTHORS_SIZE);
    }
}
