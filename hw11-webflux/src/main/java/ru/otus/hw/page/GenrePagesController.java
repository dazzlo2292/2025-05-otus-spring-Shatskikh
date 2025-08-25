package ru.otus.hw.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
@RequiredArgsConstructor
public class GenrePagesController {

    @GetMapping("/genres")
    public String listGenresPage() {
        return "genres";
    }
}
