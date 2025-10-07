package ru.otus.hw.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Comment;

@AllArgsConstructor
@Data
public class CommentDto {

    private long id;

    private String text;

    private Book book;

    public Comment toDomainObject() {
        return new Comment(id, text, book);
    }

    public static CommentDto fromDomainObject(Comment comment) {
        return new CommentDto(comment.getId(), comment.getText(), comment.getBook());
    }
}
