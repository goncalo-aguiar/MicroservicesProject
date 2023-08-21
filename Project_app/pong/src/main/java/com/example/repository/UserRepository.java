package com.example.repository;

import com.example.domain.User;
import io.micronaut.data.annotation.Query;
import io.micronaut.data.annotation.Repository;
import io.micronaut.data.repository.CrudRepository;
import java.util.Optional;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);

    @Query("UPDATE User u SET u.bestScore = :bestScore WHERE u.username = :id")
    void updateBestScore(String id, int bestScore);

    default User update(User user) {
        return save(user);
    }
}
