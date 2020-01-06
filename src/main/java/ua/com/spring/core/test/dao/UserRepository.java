package ua.com.spring.core.test.dao;

import ua.com.spring.core.test.domain.User;

import java.util.*;
import java.util.stream.Collectors;

public class UserRepository implements AbstractRepository<User>{

    public static Map<Long, User> users;

    static {
        users = new HashMap<>();
        users.put(1L, new User());
        users.put(2L, new User());
        users.put(3L, new User());
    }

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
