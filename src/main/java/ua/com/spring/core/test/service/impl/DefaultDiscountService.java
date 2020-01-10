package ua.com.spring.core.test.service.impl;

import lombok.extern.slf4j.Slf4j;
import ua.com.spring.core.test.domain.Event;
import ua.com.spring.core.test.domain.User;
import ua.com.spring.core.test.service.DiscountService;
import ua.com.spring.core.test.service.DiscountStrategy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Slf4j
public class DefaultDiscountService implements DiscountService {

    private List<DiscountStrategy> discountStrategies;

    public DefaultDiscountService(List<DiscountStrategy> discountStrategies) {
        this.discountStrategies = discountStrategies;
    }

    @Override
    public Byte getDiscount(@Nullable User user, @Nonnull Event event, long numberOfTickets) {
        Optional<Byte> discount = discountStrategies.stream().map(e -> e.execute(user, numberOfTickets))
                .max(Comparator.comparing(Byte::byteValue));

            log.info("Discount for event {} for {} tickets is: {}", event.getName(), numberOfTickets, discount.get());

            return discount.get();
    }
}
