package ru.otus.hw.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw.models.Book;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDto {

    private long id;

    private String title;

    private Long authorId;

    private String authorName;

    private Long genreId;

    private String genreName;

    public Book toDomainObject() {
        return new Book(id, title, authorId, authorName, genreId, genreName);
    }

    public static BookDto fromDomainObject(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthorId(),
                book.getAuthorName(),
                book.getGenreId(),
                book.getGenreName()
        );
    }
}
