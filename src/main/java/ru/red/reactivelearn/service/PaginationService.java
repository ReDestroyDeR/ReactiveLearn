package ru.red.reactivelearn.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * @author Daniil Shreyder
 * Date: 25.08.2021
 */
public interface PaginationService<T, ID> {
    Mono<Page<T>> getPage(ReactiveSortingRepository<T, ID> repository, Pageable pageable);

    Mono<Page<T>> getPage(Flux<T> data, Pageable pageable);
}
