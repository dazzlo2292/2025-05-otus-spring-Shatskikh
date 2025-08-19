package ru.otus.hw.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class AuthorPagesController {

    @GetMapping("/authors")
    public String listAuthorsPage() {
        return "authors";
    }
}
