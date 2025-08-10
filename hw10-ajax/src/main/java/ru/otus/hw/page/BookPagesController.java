package ru.otus.hw.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public String editBookPage(@RequestParam("id") long id, Model model) {
        model.addAttribute("bookId", id);

        List<AuthorDto> authors = authorService.findAll();
        model.addAttribute("authors", authors);

        List<GenreDto> genres = genreService.findAll();
        model.addAttribute("genres", genres);

        return "edit_book";
    }

    @GetMapping("/add/book")
    public String addBookPage(Model model) {
        BookDto newBook = new BookDto();
        model.addAttribute("book", newBook);

        List<AuthorDto> authors = authorService.findAll();
        model.addAttribute("authors", authors);

        List<GenreDto> genres = genreService.findAll();
        model.addAttribute("genres", genres);

        return "add_book";
    }
}
