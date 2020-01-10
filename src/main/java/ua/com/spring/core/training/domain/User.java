package ua.com.spring.core.training.domain;

import lombok.*;

import java.time.LocalDate;
import java.util.NavigableSet;
import java.util.Objects;
import java.util.TreeSet;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class User extends DomainObject {

    private String firstName;

    private String lastName;

    private String email;

    @ToString.Exclude
    private NavigableSet<Ticket> tickets = new TreeSet<>();

    private LocalDate birthday;

    public User(String firstName, String lastName, String email, LocalDate birthday) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.birthday = birthday;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        if (!super.equals(o)) return false;
        User user = (User) o;
        return firstName.equals(user.firstName) &&
                lastName.equals(user.lastName) &&
                email.equals(user.email) &&
                birthday.equals(user.birthday);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), firstName, lastName, email, birthday);
    }
}
