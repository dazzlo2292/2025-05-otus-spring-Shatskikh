package ru.otus.hw.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "books")
public class BookProjection {

    @Id
    private long id;

    private String title;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Long authorId;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Long genreId;
}
