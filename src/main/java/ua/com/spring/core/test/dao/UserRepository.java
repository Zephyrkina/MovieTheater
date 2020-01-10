package ua.com.spring.core.test.dao;

import org.springframework.stereotype.Repository;
import ua.com.spring.core.test.domain.User;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class UserRepository implements AbstractRepository<User>{

    private static AtomicLong userIdCount = new AtomicLong(0);

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
        if (object.getId() == null) {
            object.setId(userIdCount.getAndIncrement());
        }
        return users.put(object.getId(), object);
    }

    @Override
    public User remove(User object) {
        return users.remove(object);
    }

}
