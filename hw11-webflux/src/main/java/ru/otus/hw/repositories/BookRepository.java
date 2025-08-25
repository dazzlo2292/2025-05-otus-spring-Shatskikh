package ru.otus.hw.repositories;

import lombok.NonNull;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import ru.otus.hw.models.BookProjection;


public interface BookRepository extends ReactiveCrudRepository<BookProjection, Long>, BookRepositoryCustom {
    @NonNull
    Flux<BookProjection> findAll();
}
