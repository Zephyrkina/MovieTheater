package ua.com.spring.core.test.service.impl;

import ua.com.spring.core.test.domain.Event;
import ua.com.spring.core.test.domain.User;
import ua.com.spring.core.test.service.DiscountService;
import ua.com.spring.core.test.service.DiscountStrategy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.List;

public class DefaultDiscountService implements DiscountService {

    private List<DiscountStrategy> discountStrategies;

    public DefaultDiscountService(List<DiscountStrategy> discountStrategies) {
        this.discountStrategies = discountStrategies;
    }

    @Override
    public byte getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets) {
        return 0;
    }
}
