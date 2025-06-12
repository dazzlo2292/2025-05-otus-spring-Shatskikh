package ru.otus.hwtest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.service.IOService;
import ru.otus.hw.service.TestServiceImpl;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class TestServiceImplTest {

    private QuestionDao csvQuestionDao;

    private TestServiceImpl testService;

    @BeforeEach
    void setUp() {
        IOService ioService = mock(IOService.class);
        csvQuestionDao = mock(QuestionDao.class);
        testService = new TestServiceImpl(ioService, csvQuestionDao);
    }

    @Test
    void shouldExecFindAllMethodAndPrintQuestions() throws FileNotFoundException, URISyntaxException {
        Answer answer1 = new Answer("First answer", false);
        Answer answer2 = new Answer("Second answer", true);

        List<Answer> answers = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        answers.add(answer1);
        answers.add(answer2);

        Question question = new Question("First question", answers);

        questions.add(question);

        when(csvQuestionDao.findAll()).thenReturn(questions);

        testService.executeTest();

        verify(csvQuestionDao, times(1)).findAll();
    }
}
