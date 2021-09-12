package ru.red.reactivelearn.service.pagination;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;

/**
 * @author Daniil Shreyder
 * Date: 25.08.2021
 */

@Service
public class PaginationServiceImpl<T, ID> implements PaginationService<T, ID> {
    @Override
    public Mono<Page<T>> getPage(ReactiveSortingRepository<T, ID> repository, Pageable pageable) {
        return repository.count()
                .flatMap(count -> transform(repository.findAll(pageable.getSort()), pageable, count));
    }

    @Override
    public Mono<Page<T>> getPage(Flux<T> data, Pageable pageable) {
        return data.count()
                .flatMap(count -> transform(data, pageable, count));
    }

    private Mono<Page<T>> transform(Flux<T> data, Pageable pageable, Long count) {
        return data.buffer(pageable.getPageSize(), (pageable.getPageNumber() + 1))
                .elementAt(pageable.getPageNumber(), new ArrayList<>())
                .map(entities -> new PageImpl<>(entities, pageable, count));
    }
}
