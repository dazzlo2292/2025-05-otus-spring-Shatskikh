package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;

import java.util.List;

@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public void executeTest() {
        try {
            ioService.printLine("");
            ioService.printFormattedLine("Please answer the questions below%n");

            List<Question> questions = questionDao.findAll();

            printQuestions(questions);
        } catch (Exception e) {
            ioService.printLine("Error loading a test!");
        }
    }

    private void printQuestions(List<Question> questions) {
        for (Question q : questions) {
            ioService.printLine(q.text());
            for (Answer a : q.answers()) {
                ioService.printLine("— " + a.text());
            }
            ioService.printLine("\n");
        }
    }
}
