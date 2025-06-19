package ru.otus.hw.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.hw.dao.QuestionDao;
import ru.otus.hw.domain.Answer;
import ru.otus.hw.domain.Question;
import ru.otus.hw.domain.Student;
import ru.otus.hw.domain.TestResult;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {

    private final IOService ioService;

    private final QuestionDao questionDao;

    @Override
    public TestResult executeTestFor(Student student) {
        ioService.printLine("");
        ioService.printFormattedLine("Please answer the questions below%n");
        var questions = questionDao.findAll();
        var testResult = new TestResult(student);

        for (var question: questions) {
            var isAnswerValid = false;

            printQuestion(question);

            var userAnswer = ioService.readString();
            ioService.printLine("");

            isAnswerValid = checkCorrectAnswer(question, userAnswer);
            testResult.applyAnswer(question, isAnswerValid);
        }
        return testResult;
    }

    private void printQuestion(Question question) {
        ioService.printLine(question.text());
        for (Answer a : question.answers()) {
            ioService.printFormattedLine("— %s", a.text());
        }
        ioService.printLine("Input answer:");
    }

    private boolean checkCorrectAnswer(Question question, String userAnswer) {
        List<Answer> answers = question.answers();
        Answer correctAnswer = null;

        for (Answer answer : answers) {
            if (answer.isCorrect()) {
                correctAnswer = answer;
                break;
            }
        }

        return correctAnswer.text().equals(userAnswer);
    }
}
