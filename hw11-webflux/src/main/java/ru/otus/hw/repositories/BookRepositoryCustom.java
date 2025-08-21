package ru.otus.hw.repositories;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.models.BookInfo;

public interface BookRepositoryCustom {
    Mono<BookInfo> findById(long id);

    Flux<BookInfo> findAllBooksWithAuthorAndGenre();
}
