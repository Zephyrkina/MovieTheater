package ua.com.spring.core.training.domain;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public enum Discount {
    NO_DISCOUNT(1, (byte) 0),
    BIRTHDAY_DISCOUNT(2, (byte) 5),
    EVERY_10TH_TICKET_DISCOUNT(3, (byte) 50);

    private long id;
    private byte value;

    Discount() {
    }

    Discount(long id, byte value) {
        this.id = id;
        this.value = value;
    }
}
