package ru.otus.hw.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.hw.models.Comment;

@AllArgsConstructor
@Data
public class CommentDto {

    private long id;

    private String text;

    private BookDto book;

    public Comment toDomainObject() {
        return new Comment(id, text, book.toDomainObject());
    }

    public static CommentDto fromDomainObject(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                BookDto.fromDomainObject(comment.getBook()));
    }
}
