package ru.otus.hw.rest.dto;

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

    private String title;

    private Author author;

    private Genre genre;

    public Book toDomainBookObject() {
        return new Book(id, title, author, genre);
    }

    public static BookDto fromDomainObject(Book bookInfo) {
        return new BookDto(
                bookInfo.getId(),
                bookInfo.getTitle(),
                bookInfo.getAuthor(),
                bookInfo.getGenre()
        );
    }
}
