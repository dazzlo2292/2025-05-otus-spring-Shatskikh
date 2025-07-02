package ru.otus.hw.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Question;


import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@DisplayName("Тестирование CsvQuestionDao")
@SpringBootTest
public class CsvQuestionDaoTest {

    @MockitoBean
    private TestFileNameProvider fileNameProvider;

    @Autowired
    private CsvQuestionDao csvQuestionDao;


    @DisplayName("Возврат вопросов из файла")
    @Test
    void shouldReturnQuestionsFromFile() {

        when(fileNameProvider.getTestFileName()).thenReturn("questions.csv");

        List<Question> questions = csvQuestionDao.findAll();

        assertEquals(questions.size(), 2);
    }
}
