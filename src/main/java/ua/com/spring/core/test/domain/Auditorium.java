package ua.com.spring.core.test.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.LongStream;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Auditorium extends DomainObject{

    private String name;

    private Long numberOfSeats;

    private Set<Long> vipSeats = Collections.emptySet();
    
    public Set<Long> getAllSeats() {
        return LongStream.range(1, numberOfSeats+1).boxed().collect(Collectors.toSet());
    }
}
