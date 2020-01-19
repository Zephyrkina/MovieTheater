package ua.com.spring.core.training.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DiscountCounter {
    private long discountId;
    private long givenTotalTimes;
}
