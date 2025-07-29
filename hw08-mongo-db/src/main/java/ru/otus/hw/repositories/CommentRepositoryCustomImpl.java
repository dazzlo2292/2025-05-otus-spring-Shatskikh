package ru.otus.hw.repositories;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.otus.hw.models.Comment;


@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {

    private final MongoTemplate mongoTemplate;

    @Override
    public void removeCommentsByBookId(String id) {
        Query query = new Query();
        query.addCriteria(Criteria.where("book").is(id));

        mongoTemplate.remove(query, Comment.class, "comments");
    }
}
