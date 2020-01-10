package ua.com.spring.core.training.service.impl;

import ua.com.spring.core.training.dao.UserRepository;
import ua.com.spring.core.training.domain.User;
import ua.com.spring.core.training.exceptions.UserNotFoundException;
import ua.com.spring.core.training.service.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Optional;

public class DefaultUserService implements UserService {

    private UserRepository userRepository;

    public DefaultUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        Optional<User> user = userRepository.getByEmail(email);
        return user.orElseThrow(() -> new UserNotFoundException("No user with such email found"));
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
        return userRepository.getById(id).orElseThrow(() -> new UserNotFoundException("No user with such id found"));
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return userRepository.getAll();
    }
}
