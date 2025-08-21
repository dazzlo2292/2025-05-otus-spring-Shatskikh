package ru.otus.hw.models;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookInfo {

    private long id;

    private String title;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Author author;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Genre genre;
}
