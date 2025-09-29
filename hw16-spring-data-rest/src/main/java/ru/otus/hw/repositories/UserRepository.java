package ru.otus.hw.repositories;


import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.otus.hw.auth.User;

public interface UserRepository extends JpaRepository<User, Long> {
    @EntityGraph(value = "user-role-entity-graph")
    User findByUsername(String username);
}
