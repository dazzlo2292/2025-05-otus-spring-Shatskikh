package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.rest.dto.BookProjectionDto;
import ru.otus.hw.rest.dto.BookDto;
import ru.otus.hw.rest.exceptions.EntityNotFoundException;
import ru.otus.hw.models.BookProjection;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;


@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {
    private final AuthorRepository authorRepository;

    private final GenreRepository genreRepository;

    private final BookRepository bookRepository;

    @Override
    public Mono<BookDto> findById(long id) {
        return bookRepository.findById(id)
                .map(BookDto::fromDomainObject);
    }

    @Override
    public Flux<BookDto> findAll() {
        return bookRepository.findAllBooksWithAuthorAndGenre()
                .map(BookDto::fromDomainObject);
    }

    @Override
    public Mono<Void> deleteById(long id) {
        return bookRepository.deleteById(id);
    }

    @Override
    public Mono<BookProjectionDto> save(BookDto book) {
        var authorId = book.getAuthor().getId();
        var genreId = book.getGenre().getId();

        var author = authorRepository.findById(authorId)
                .switchIfEmpty(Mono.error(
                        new EntityNotFoundException("Author with id %d not found".formatted(authorId))
                ));
        var genre = genreRepository.findById(genreId)
                .switchIfEmpty(Mono.error(
                        new EntityNotFoundException("Genre with id %d not found".formatted(genreId))
                ));

        BookProjection newBook = new BookProjection(book.getId(),
                book.getTitle(),
                authorId,
                genreId);

        return bookRepository.save(newBook)
                .map(BookProjectionDto::fromDomainObject);
    }
}
