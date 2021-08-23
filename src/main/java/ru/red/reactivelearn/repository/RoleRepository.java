package ru.red.reactivelearn.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import ru.red.reactivelearn.model.general.Role;

/**
 * @author Daniil Shreyder
 * Date: 22.08.2021
 */

@Repository
public interface RoleRepository extends ReactiveMongoRepository<Role, String> {
}
