package ru.otus.hw.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import ru.otus.hw.rest.dto.GenreDto;
import ru.otus.hw.repositories.GenreRepository;


@RequiredArgsConstructor
@Service
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    @Override
    public Flux<GenreDto> findAll() {
        return genreRepository.findAll()
                .map(GenreDto::fromDomainObject);

//        return genreRepository.findAll()
//                .stream()
//                .map(GenreDto::fromDomainObject)
//                .toList();
    }
}
