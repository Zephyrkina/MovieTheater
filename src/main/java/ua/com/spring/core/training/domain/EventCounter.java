package ua.com.spring.core.training.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventCounter {
    private long eventId;
    private long nameRequests = 0;
    private long priceRequests = 0;
    private long bookRequests = 0 ;
}
