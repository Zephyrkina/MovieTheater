package ua.com.spring.core.training.service;

import ua.com.spring.core.training.domain.Discount;
import ua.com.spring.core.training.domain.User;

public interface UserDiscountCounterService {

    Integer increaseGivenUserDiscountCounter(Discount discountRating, User user);

}
