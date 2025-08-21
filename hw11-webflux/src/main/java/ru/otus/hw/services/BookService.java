package ru.otus.hw.services;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.rest.dto.BookDto;
import ru.otus.hw.rest.dto.BookInfoDto;


public interface BookService {
    Mono<BookInfoDto> findById(long id);

    Flux<BookInfoDto> findAll();

    Mono<BookDto> save(BookInfoDto book);

    Mono<Void> deleteById(long id);
}
