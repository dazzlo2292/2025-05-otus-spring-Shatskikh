package ru.otus.hw.dao;

import ru.otus.hw.dao.dto.QuestionDto;

import java.io.Reader;
import java.util.List;

public interface QuestionsParser {
    List<QuestionDto> parse(Reader reader);
}
