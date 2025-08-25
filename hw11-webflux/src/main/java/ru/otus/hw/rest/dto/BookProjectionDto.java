package ru.otus.hw.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw.models.BookProjection;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookProjectionDto {

    private long id;

    private String title;

    private Long authorId;

    private Long genreId;

    public BookProjection toDomainObject() {
        return new BookProjection(id, title, authorId, genreId);
    }

    public static BookProjectionDto fromDomainObject(BookProjection book) {
        return new BookProjectionDto(
                book.getId(),
                book.getTitle(),
                book.getAuthorId(),
                book.getGenreId()
        );
    }
}
