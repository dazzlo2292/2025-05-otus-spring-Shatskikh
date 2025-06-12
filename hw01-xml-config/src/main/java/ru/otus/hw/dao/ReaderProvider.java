package ru.otus.hw.dao;

import java.io.Reader;

public interface ReaderProvider {
    Reader getReader(String fileName);
}
