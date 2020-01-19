package ua.com.spring.core.training.dao.impl;

import ua.com.spring.core.training.dao.UserDiscountCounterRepository;
import ua.com.spring.core.training.domain.UserDiscountCounter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class UserDiscountCounterRepositoryImpl implements UserDiscountCounterRepository {

    private static Map<Long, UserDiscountCounter> userDiscountCounters = new HashMap<>();

    @Override
    public Optional<UserDiscountCounter> getById(Long id) {
        return Optional.ofNullable(userDiscountCounters.get(id));
    }

    @Override
    public UserDiscountCounter save(UserDiscountCounter object) {
        return userDiscountCounters.put(object.getUserId(), object);
    }
}
