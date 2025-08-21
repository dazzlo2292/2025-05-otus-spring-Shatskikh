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

    private Long genreId;

    public Book toDomainObject() {
        return new Book(id, title, authorId, genreId);
    }

    public static BookDto fromDomainObject(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                book.getAuthorId(),
                book.getGenreId()
        );
    }
}
