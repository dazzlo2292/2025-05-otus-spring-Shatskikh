package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.otus.hw.rest.dto.CommentDto;
import ru.otus.hw.models.Comment;
import ru.otus.hw.repositories.CommentRepository;


@RequiredArgsConstructor
@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Override
    public Mono<Comment> findById(long id) {
        return commentRepository.findById(id);
    }

    @Override
    public Flux<CommentDto> findAllByBookId(long id) {
        return commentRepository.findByBookId(id)
                .map(CommentDto::fromDomainObject);
    }

    @Override
    public void deleteById(long id) {
        commentRepository.deleteById(id);
    }

}
