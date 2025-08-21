package ru.otus.hw.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Mono;
import ru.otus.hw.rest.dto.AuthorDto;
import ru.otus.hw.rest.dto.BookDto;
import ru.otus.hw.rest.dto.GenreDto;
import ru.otus.hw.services.AuthorService;
import ru.otus.hw.services.GenreService;

import java.util.List;


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
        Mono<List<AuthorDto>> authorsMono = authorService.findAll().collectList();
        Mono<List<GenreDto>> genresMono = genreService.findAll().collectList();

        return Mono.zip(authorsMono, genresMono)
                .map(tuple -> {
                    List<AuthorDto> authors = tuple.getT1();
                    List<GenreDto> genres = tuple.getT2();

                    model.addAttribute("bookId", id);
                    model.addAttribute("authors", authors);
                    model.addAttribute("genres", genres);

                    return "edit_book";
                });
    }

    @GetMapping("/add/book")
    public Mono<String> addBookPage(Model model) {
        BookDto newBook = new BookDto();
        Mono<List<AuthorDto>> authorsMono = authorService.findAll().collectList();
        Mono<List<GenreDto>> genresMono = genreService.findAll().collectList();

        return Mono.zip(authorsMono, genresMono)
                .map(tuple -> {
                    List<AuthorDto> authors = tuple.getT1();
                    List<GenreDto> genres = tuple.getT2();

                    model.addAttribute("book", newBook);
                    model.addAttribute("authors", authors);
                    model.addAttribute("genres", genres);

                    return "add_book";
                });
    }
}
