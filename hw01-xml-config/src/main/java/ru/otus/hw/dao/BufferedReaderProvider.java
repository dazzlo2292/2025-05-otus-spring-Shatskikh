package ru.otus.hw.dao;

import lombok.RequiredArgsConstructor;
import ru.otus.hw.exceptions.QuestionReadException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class BufferedReaderProvider implements ReaderProvider {
    @Override
    public Reader getReader(String fileName) throws QuestionReadException {
        InputStream inputStreamForResource = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStreamForResource == null) {
            throw new QuestionReadException("File with questions not found!");
        } else {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStreamForResource, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            return bufferedReader;
        }
    }
}
