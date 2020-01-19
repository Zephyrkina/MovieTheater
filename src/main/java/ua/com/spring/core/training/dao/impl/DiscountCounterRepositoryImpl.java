package ua.com.spring.core.training.dao.impl;

import ua.com.spring.core.training.dao.DiscountCounterRepository;
import ua.com.spring.core.training.domain.DiscountCounter;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DiscountCounterRepositoryImpl implements DiscountCounterRepository {

    private static Map<Long, DiscountCounter> discountCounters = new HashMap<>();

    @Override
    public Optional<DiscountCounter> getById(Long id) {
        return Optional.ofNullable(discountCounters.get(id));
    }

    @Override
    public DiscountCounter save(DiscountCounter object) {
        return discountCounters.put((long) object.getDiscountId(), object);
    }
}
