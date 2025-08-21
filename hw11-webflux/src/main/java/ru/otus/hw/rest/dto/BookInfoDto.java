package ru.otus.hw.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.BookInfo;
import ru.otus.hw.models.Genre;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookInfoDto {

    private long id;

    private String title;

    private Author author;

    private Genre genre;

    public BookInfo toDomainBookObject() {
        return new BookInfo(id, title, author, genre);
    }

    public static BookInfoDto fromDomainObject(BookInfo bookInfo) {
        return new BookInfoDto(
                bookInfo.getId(),
                bookInfo.getTitle(),
                bookInfo.getAuthor(),
                bookInfo.getGenre()
        );
    }
}
