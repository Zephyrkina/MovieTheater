package ua.com.spring.core.test.dao;

import org.springframework.stereotype.Repository;
import ua.com.spring.core.test.domain.User;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserRepository implements AbstractRepository<User>{

    private static Map<Long, User> users = new HashMap<>();

    public List<User> getAll() {
        return new ArrayList<>(users.values());
    }

    @Override
    public Optional<User> getById(Long id) {
        return Optional.of(users.get(id));
    }

    public Optional<User> getByEmail(String email) {
        return users.values().stream().filter(u -> u.getEmail().equals(email)).findFirst();
    }

    @Override
    public User save(User object) {
        return users.put(object.getId(), object);
    }

    @Override
    public User remove(User object) {
        return users.remove(object);
    }

    @Override
    public User update(User object) {
        users.remove(object.getId());
        return users.put(object.getId(), object);
    }
}
