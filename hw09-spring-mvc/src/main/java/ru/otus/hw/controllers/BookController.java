package ru.otus.hw.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.otus.hw.dto.AuthorDto;
import ru.otus.hw.dto.BookDto;
import ru.otus.hw.dto.GenreDto;
import ru.otus.hw.exceptions.EntityNotFoundException;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.BookService;
import ru.otus.hw.services.GenreService;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/")
    public String listPage(Model model) {
        List<BookDto> books = bookService.findAll().stream()
                .map(BookDto::fromDomainObject).toList();
        model.addAttribute("books", books);
        return "books";
    }

    @GetMapping("/edit/book")
    public String editPage(@RequestParam("id") long id, Model model) {
        Optional<BookDto> book = bookService.findById(id)
                .map(BookDto::fromDomainObject);
        if (book.isEmpty()) {
            throw new EntityNotFoundException("Book not found");
        }
        model.addAttribute("book", book.get());

        List<AuthorDto> authors = authorService.findAll().stream()
                .map(AuthorDto::fromDomainObject).toList();
        model.addAttribute("authors", authors);

        List<GenreDto> genres = genreService.findAll().stream()
                .map(GenreDto::fromDomainObject).toList();
        model.addAttribute("genres", genres);

        return "edit_book";
    }

    @PostMapping("/edit/book")
    public String saveBook(@Valid @ModelAttribute("book") BookDto book,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("book", book);

            List<AuthorDto> authors = authorService.findAll().stream()
                    .map(AuthorDto::fromDomainObject).toList();
            model.addAttribute("authors", authors);

            List<GenreDto> genres = genreService.findAll().stream()
                    .map(GenreDto::fromDomainObject).toList();
            model.addAttribute("genres", genres);

            return "edit_book";
        }
        bookService.save(book.toDomainObject());
        return "redirect:/";
    }

    @GetMapping("/add/book")
    public String addPage(Model model) {
        BookDto newBook = new BookDto();
        model.addAttribute("book", newBook);

        List<AuthorDto> authors = authorService.findAll().stream()
                .map(AuthorDto::fromDomainObject).toList();
        model.addAttribute("authors", authors);

        List<GenreDto> genres = genreService.findAll().stream()
                .map(GenreDto::fromDomainObject).toList();
        model.addAttribute("genres", genres);

        return "add_book";
    }

    @DeleteMapping("/delete/book")
    public String deleteBook(@RequestParam("id") long id) {
        bookService.deleteById(id);
        return "redirect:/";
    }
}
