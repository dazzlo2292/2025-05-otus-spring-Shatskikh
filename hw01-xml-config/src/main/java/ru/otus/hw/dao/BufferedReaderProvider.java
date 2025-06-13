package ru.otus.hw.dao;

import lombok.RequiredArgsConstructor;

import java.io.*;
import java.nio.charset.StandardCharsets;

@RequiredArgsConstructor
public class BufferedReaderProvider implements ReaderProvider {
    @Override
    public Reader getReader(String fileName) throws FileNotFoundException {
        InputStream inputStreamForResource = getClass().getClassLoader().getResourceAsStream(fileName);
        if (inputStreamForResource == null) {
            throw new FileNotFoundException("File " + fileName + "not found!");
        } else {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStreamForResource, StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            return bufferedReader;
        }
    }
}
