package ua.com.spring.core.test.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.com.spring.core.test.dao.UserRepository;
import ua.com.spring.core.test.domain.User;
import ua.com.spring.core.test.service.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;

@Service
public class DefaultUserService implements UserService {

    private UserRepository userRepository;

    @Autowired
    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        Optional<User> user = userRepository.getByEmail(email);
        return user.orElseThrow(() -> new RuntimeException("No user with such email found"));
    }

    @Override
    public User save(@Nonnull User object) {
        return userRepository.save(object);
    }

    @Override
    public void remove(@Nonnull User object) {
        userRepository.remove(object);
    }

    @Override
    public User getById(@Nonnull Long id) {
        return userRepository.getById(id).orElseThrow(() -> new RuntimeException("No user with such id found"));
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return userRepository.getAll();
    }

    @Override
    public User update(User object) {
        return userRepository.update(object);
    }
}
