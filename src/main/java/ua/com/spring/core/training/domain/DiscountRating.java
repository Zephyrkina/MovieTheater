package ua.com.spring.core.training.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum DiscountRating {
    NO_DISCOUNT((byte) 0),
    BIRTHDAY_DISCOUNT((byte) 5),
    EVERY_10TH_TICKET_DISCOUNT((byte) 50);

    private byte discountValue;

    DiscountRating(byte discountValue) {
        this.discountValue = discountValue;
    }
}
