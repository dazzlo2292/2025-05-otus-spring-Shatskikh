package ru.otus.hw.mongock.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.otus.hw.models.Author;
import ru.otus.hw.models.Book;
import ru.otus.hw.models.Genre;
import ru.otus.hw.repositories.AuthorRepository;
import ru.otus.hw.repositories.BookRepository;
import ru.otus.hw.repositories.GenreRepository;

@ChangeLog(order = "001")
public class DatabaseChangelog {

    private Author firstAuthor;
    private Author secondAuthor;

    private Genre firstGenre;
    private Genre secondGenre;

    @ChangeSet(order = "000", id = "dropDb", author = "admin", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    @ChangeSet(order = "001", id = "initAuthors", author = "admin", runAlways = true)
    public void initAuthors(AuthorRepository repository){
        firstAuthor = repository.save(new Author("1", "Author_1"));
        secondAuthor = repository.save(new Author("2", "Author_2"));
    }

    @ChangeSet(order = "002", id = "initGenres", author = "admin", runAlways = true)
    public void initGenres(GenreRepository repository){
        firstGenre = repository.save(new Genre("1", "Genre_1"));
        secondGenre= repository.save(new Genre("2", "Genre_2"));
    }

    @ChangeSet(order = "003", id = "initBooks", author = "admin", runAlways = true)
    public void initBooks(BookRepository repository){
        repository.save(new Book("1", "BookTitle_1", firstAuthor, firstGenre));
        repository.save(new Book("2", "BookTitle_2", secondAuthor, secondGenre));
    }
}
