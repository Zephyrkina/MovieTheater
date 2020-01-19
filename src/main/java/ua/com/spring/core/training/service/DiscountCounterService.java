package ua.com.spring.core.training.service;

import ua.com.spring.core.training.domain.Discount;

public interface DiscountCounterService {

    Long increaseTotalGivenDiscountCounter(Discount discountRating);

}
