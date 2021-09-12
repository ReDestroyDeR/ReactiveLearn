package ru.red.reactivelearn.datainitializer;

import lombok.extern.java.Log;
import org.springframework.stereotype.Component;
import ru.red.reactivelearn.model.general.Role;
import ru.red.reactivelearn.repository.RoleRepository;

import java.util.Set;
import java.util.logging.Level;

/**
 * @author Daniil Shreyder
 * Date: 22.08.2021
 */

@Log
@Component
public class RolesDataInitializer {

    public RolesDataInitializer(RoleRepository roleRepository) {

        roleRepository.saveAll(Set.of(Role.values()))
                .subscribe(r -> log.log(Level.INFO, "Saved role : " + r.name()));
    }
}
