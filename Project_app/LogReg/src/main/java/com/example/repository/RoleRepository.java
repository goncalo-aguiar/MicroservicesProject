package com.example.repository;

import com.example.domain.Role;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;

import java.util.Optional;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findByName(String name);

    default Role findOrCreateByName(String name) {
        return findByName(name).orElseGet(() -> {
            Role role = new Role();
            role.setName(name);
            save(role);
            return role;
        });
    }
}
