package ua.com.spring.core.test.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.NavigableSet;
import java.util.TreeSet;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User extends DomainObject {

    private String firstName;

    private String lastName;

    private String email;

    private NavigableSet<Ticket> tickets = new TreeSet<>();

    private LocalDate birthday;

    public User(String firstName, String lastName, String email, LocalDate birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
    }
}
