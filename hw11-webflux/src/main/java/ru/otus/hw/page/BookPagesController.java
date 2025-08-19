package ru.otus.hw.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;
import ru.otus.hw.rest.dto.BookDto;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.GenreService;


@Controller
@RequiredArgsConstructor
public class BookPagesController {

    private final AuthorService authorService;

    private final GenreService genreService;

    @GetMapping("/")
    public String listBooksPage() {
        return "books";
    }

    @GetMapping("/edit/book")
    public Mono<String> editBookPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("bookId", id);

        authorService.findAll()
                .collectList()
                .doOnNext(authors -> model.addAttribute("authors", authors));

        genreService.findAll()
                .collectList()
                .doOnNext(genres -> model.addAttribute("genres", genres));

        return Mono.just("edit_book");
    }

    @GetMapping("/add/book")
    public Mono<String> addBookPage(Model model) {
        BookDto newBook = new BookDto();
        model.addAttribute("book", newBook);

        authorService.findAll()
                .collectList()
                .doOnNext(authors -> model.addAttribute("authors", authors));

        genreService.findAll()
                .collectList()
                .doOnNext(genres -> model.addAttribute("genres", genres));

        return Mono.just("add_book");
    }
}
