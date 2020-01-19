package ua.com.spring.core.training.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDiscountCounter {
    private long userId;
    private long discountId;
    private int givenTimes;
}
