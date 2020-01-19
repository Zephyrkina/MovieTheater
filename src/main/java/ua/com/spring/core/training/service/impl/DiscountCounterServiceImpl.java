package ua.com.spring.core.training.service.impl;

import ua.com.spring.core.training.dao.DiscountCounterRepository;
import ua.com.spring.core.training.domain.Discount;
import ua.com.spring.core.training.domain.DiscountCounter;
import ua.com.spring.core.training.service.DiscountCounterService;

public class DiscountCounterServiceImpl implements DiscountCounterService {

    private DiscountCounterRepository discountCounterRepository;

    public DiscountCounterServiceImpl(DiscountCounterRepository discountCounterRepository) {
        this.discountCounterRepository = discountCounterRepository;
    }

    @Override
    public Long increaseTotalGivenDiscountCounter(Discount discount) {
        DiscountCounter discountCounter = discountCounterRepository.getById(discount.getId())
                .orElse(createDiscountCounter(discount));

        long newCounterValue = discountCounter.getGivenTotalTimes() + 1L;
        discountCounter.setGivenTotalTimes(newCounterValue);

        discountCounterRepository.save(discountCounter);

        return newCounterValue;
    }

    private DiscountCounter createDiscountCounter(Discount discount) {
        return DiscountCounter.builder()
                .discountId(discount.getId())
                .build();
    }
}
