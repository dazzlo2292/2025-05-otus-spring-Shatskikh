package ru.otus.hw.dao;

import com.opencsv.bean.CsvToBeanBuilder;
import ru.otus.hw.dao.dto.QuestionDto;

import java.io.Reader;
import java.util.List;

public class CsvQuestionsParser implements QuestionsParser {
    @Override
    public List<QuestionDto> parse(Reader reader) {
        List<QuestionDto> questionsDto = new CsvToBeanBuilder<QuestionDto>(reader)
                .withType(QuestionDto.class)
                .withSkipLines(1)
                .withSeparator(';')
                .build()
                .parse();
        return questionsDto;
    }
}
