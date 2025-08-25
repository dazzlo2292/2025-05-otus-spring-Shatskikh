package ru.otus.hw.rest.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.rest.dto.BookProjectionDto;
import ru.otus.hw.rest.dto.BookDto;
import ru.otus.hw.services.BookService;


@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/api/v1/book")
    public Flux<BookDto> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/api/v1/book/{id}")
    public Mono<BookDto> getBook(@PathVariable("id") long id) {
        Mono<BookDto> book = bookService.findById(id);
        return book;
    }

    @PostMapping("/api/v1/book")
    public Mono<ResponseEntity<BookProjectionDto>> addBook(@Valid @RequestBody BookDto book) {
        Mono<BookProjectionDto> savedBook = bookService.save(book);
        return savedBook
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/api/v1/book/{id}")
    public Mono<Void> deleteBook(@PathVariable("id") long id) {
        return bookService.deleteById(id);
    }
}
