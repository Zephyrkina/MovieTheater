package ua.com.spring.core.training.dao;

import ua.com.spring.core.training.domain.User;

import java.util.Optional;

public interface UserRepository extends AbstractRepository<User> {
    Optional<User> getByEmail(String email);
}
