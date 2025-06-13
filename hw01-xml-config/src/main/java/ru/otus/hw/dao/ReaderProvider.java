package ru.otus.hw.dao;

import java.io.FileNotFoundException;
import java.io.Reader;

public interface ReaderProvider {
    Reader getReader(String fileName) throws FileNotFoundException;
}
