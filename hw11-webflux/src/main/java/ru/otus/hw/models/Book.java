package ru.otus.hw.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private long id;

    private String title;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Author author;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Genre genre;
}
