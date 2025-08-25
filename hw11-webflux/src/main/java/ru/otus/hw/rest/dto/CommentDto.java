package ru.otus.hw.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.otus.hw.models.Comment;

@AllArgsConstructor
@Data
public class CommentDto {

    private long id;

    private String text;

    private Long bookId;

    private String bookTitle;

    public Comment toDomainObject() {
        return new Comment(id, text, bookId, bookTitle);
    }

    public static CommentDto fromDomainObject(Comment comment) {
        return new CommentDto(
                comment.getId(),
                comment.getText(),
                comment.getBookId(),
                comment.getBookTitle());
    }
}
