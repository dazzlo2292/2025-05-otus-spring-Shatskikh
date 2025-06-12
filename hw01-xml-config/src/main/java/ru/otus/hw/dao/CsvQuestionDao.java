package ru.otus.hw.dao;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.config.TestFileNameProvider;
import ru.otus.hw.dao.dto.QuestionDto;
import ru.otus.hw.domain.Question;

import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CsvQuestionDao implements QuestionDao {
    private final TestFileNameProvider fileNameProvider;

    private final ReaderProvider readerProvider;

    private final QuestionsParser questionsParser;

    @Override
    public List<Question> findAll() {
        Reader bufferedReader = readerProvider.getReader(fileNameProvider.getTestFileName());

        List<QuestionDto> questionsDto = questionsParser.parse(bufferedReader);
        List<Question> questions = new ArrayList<>();

        for (QuestionDto q : questionsDto) {
            questions.add(q.toDomainObject());
        }
        return questions;
    }
}
