package ru.otus.hw.repositories;

import lombok.NonNull;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.otus.hw.models.Book;


public interface BookRepository extends ReactiveCrudRepository<Book, Long>, BookRepositoryCustom {
    @NonNull
    Flux<Book> findAll();
}
