package ru.otus.hw.dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.domain.Question;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CsvQuestionDaoTest {

    private CsvQuestionDao csvQuestionDao;

    private TestFileNameProvider fileNameProvider;

    private ReaderProvider readerProvider;

    private QuestionsParser questionsParser;


    @BeforeEach
    void setUp() {
        fileNameProvider = mock(TestFileNameProvider.class);
        readerProvider = mock(ReaderProvider.class);
        questionsParser = new CsvQuestionsParser();
        csvQuestionDao = new CsvQuestionDao(fileNameProvider, readerProvider, questionsParser);
    }

    @Test
    void shouldReturnQuestionsFromFile() throws FileNotFoundException {

        InputStream inputStreamForResource = getClass().getClassLoader().getResourceAsStream("questions.csv");
        InputStreamReader inputStreamReader = new InputStreamReader(inputStreamForResource, StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

        when(fileNameProvider.getTestFileName()).thenReturn("questions.csv");
        when(readerProvider.getReader("questions.csv")).thenReturn(bufferedReader);

        List<Question> questions = csvQuestionDao.findAll();

        assertEquals(questions.size(), 2);
    }
}
