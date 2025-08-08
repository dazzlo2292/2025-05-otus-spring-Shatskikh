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
import ru.otus.hw.models.Book;
import ru.otus.hw.rest.dto.BookDto;
import ru.otus.hw.rest.exceptions.EntityNotFoundException;
import ru.otus.hw.services.BookService;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping("/api/v1/book")
    public List<BookDto> getAllBooks() {
        return bookService.findAll();
    }

    @GetMapping("/api/v1/book/{id}")
    public BookDto getBook(@PathVariable("id") long id) {
        Optional<BookDto> book = bookService.findById(id);
        if (book.isEmpty()) {
            throw new EntityNotFoundException("Book not found!");
        }
        return book.get();
    }

    @PostMapping("/api/v1/book")
    public ResponseEntity<BookDto> addBook(@Valid @RequestBody BookDto book) {
        Book savedBook = bookService.save(book.toDomainObject());
        return ResponseEntity.ok(BookDto.fromDomainObject(savedBook));
    }

    @DeleteMapping("/api/v1/book/{id}")
    public void deleteBook(@PathVariable("id") long id) {
        bookService.deleteById(id);
    }
}
