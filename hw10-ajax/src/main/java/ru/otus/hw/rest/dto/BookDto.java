package ru.otus.hw.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.hw.models.Book;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BookDto {

    private long id;

    @NotBlank(message = "Title should not be blank")
    private String title;

    @NotNull(message = "Need selected author")
    private AuthorDto author;

    @NotNull(message = "Need selected genre")
    private GenreDto genre;

    public Book toDomainObject() {
        return new Book(id, title, author.toDomainObject(), genre.toDomainObject());
    }

    public static BookDto fromDomainObject(Book book) {
        return new BookDto(
                book.getId(),
                book.getTitle(),
                AuthorDto.fromDomainObject(book.getAuthor()),
                GenreDto.fromDomainObject(book.getGenre()));
    }
}
