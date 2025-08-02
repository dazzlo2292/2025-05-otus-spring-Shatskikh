package ru.otus.hw.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDto {

    private long id;

    @NotBlank(message = "Title should not be blank")
    private String title;

    @NotNull(message = "Need selected author")
    private Author author;

    @NotNull(message = "Need selected genre")
    private Genre genre;

    public Book toDomainObject() {
        return new Book(id, title, author, genre);
    }

    public static BookDto fromDomainObject(Book book) {
        return new BookDto(book.getId(), book.getTitle(), book.getAuthor(), book.getGenre());
    }
}
