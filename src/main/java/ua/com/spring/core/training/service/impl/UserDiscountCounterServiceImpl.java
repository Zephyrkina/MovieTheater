package ua.com.spring.core.training.service.impl;

import ua.com.spring.core.training.dao.UserDiscountCounterRepository;
import ua.com.spring.core.training.domain.Discount;
import ua.com.spring.core.training.domain.User;
import ua.com.spring.core.training.domain.UserDiscountCounter;
import ua.com.spring.core.training.service.UserDiscountCounterService;

public class UserDiscountCounterServiceImpl implements UserDiscountCounterService {

    private UserDiscountCounterRepository userDiscountCounterRepository;

    public UserDiscountCounterServiceImpl(UserDiscountCounterRepository userDiscountCounterRepository) {
        this.userDiscountCounterRepository = userDiscountCounterRepository;
    }

    @Override
    public Integer increaseGivenUserDiscountCounter(Discount discount, User user) {


        UserDiscountCounter userDiscountCounter = userDiscountCounterRepository.getById(user.getId())
                .orElse(createUserDiscountCounter(user, discount));

        int newDiscountCountValue = userDiscountCounter.getGivenTimes() + 1;
        userDiscountCounter.setGivenTimes(newDiscountCountValue);

        userDiscountCounterRepository.save(userDiscountCounter);

        return newDiscountCountValue;
    }

    private UserDiscountCounter createUserDiscountCounter(User user, Discount discount) {
        return UserDiscountCounter.builder()
                .userId(user.getId())
                .discountId(discount.getId())
                .build();
    }
}
