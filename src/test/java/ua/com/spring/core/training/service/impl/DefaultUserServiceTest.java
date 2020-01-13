package ua.com.spring.core.training.service.impl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ua.com.spring.core.training.dao.UserRepository;
import ua.com.spring.core.training.domain.User;
import ua.com.spring.core.training.exceptions.UserNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DefaultUserServiceTest {


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private DefaultUserService userService;

    @Test
    void givenExistingEmail_whenGetByEmail_shouldReturnUser() {
        String expectedEmail = "fred@jamb.com";
        User expectedUser = new User("Fred", "Weills", expectedEmail, LocalDate.now().minusYears(30).minusDays(30));

        when(userRepository.getByEmail(expectedEmail)).thenReturn(Optional.of(expectedUser));
        User actualUser = userService.getUserByEmail(expectedEmail);

        assertThat(actualUser, equalTo(expectedUser));
        verify(userRepository).getByEmail(expectedEmail);
    }

    @Test
    void givenNonExistingEmail_whenGetByEmail_shouldThrowException() {
        String nonExistingEmail = "fred@jamb.com";

        when(userRepository.getByEmail(nonExistingEmail)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> {userService.getUserByEmail(nonExistingEmail);});

        verify(userRepository).getByEmail(nonExistingEmail);
    }

    @Test
    void givenNullEmail_whenGetByEmail_shouldThrowException() {
        String nullEmail = null;

        assertThrows(IllegalArgumentException.class, () -> {userService.getUserByEmail(nullEmail);});

        verify(userRepository, never()).getByEmail(nullEmail);

    }

    @Test
    void givenEmptyEmail_whenGetByEmail_shouldThrowException() {
        String emptyEmail = "";

        assertThrows(IllegalArgumentException.class, () -> {userService.getUserByEmail(emptyEmail);});

        verify(userRepository, never()).getByEmail(emptyEmail);
    }

    @Test
    void givenValidUser_whenSave_thenShouldSave() {
        User expectedUser = new User("Fred", "Weills", "fred@jamb.com", LocalDate.now().minusYears(30).minusDays(30));

        when(userRepository.save(expectedUser)).thenReturn(expectedUser);
        User actualUser = userRepository.save(expectedUser);

        assertThat(actualUser, equalTo(expectedUser));
        verify(userRepository).save(expectedUser);
    }

    @Test
    void givenNullUser_whenSave_thenShouldThrowException() {
        User expectedUser = null;

        assertThrows(IllegalArgumentException.class, () -> {userService.save(expectedUser);});

        verify(userRepository, never()).save(expectedUser);
    }

    @Test
    void givenValidUser_whenRemove_thenShouldRemove() {
        User expectedUser = new User("Fred", "Weills", "fred@jamb.com", LocalDate.now().minusYears(30).minusDays(30));

        when(userRepository.remove(expectedUser)).thenReturn(expectedUser);
        User actualUser = userRepository.remove(expectedUser);

        assertThat(actualUser, equalTo(expectedUser));
        verify(userRepository).remove(expectedUser);
    }

    @Test
    void givenNullUser_whenRemove_thenShouldThrowException() {
        User expectedUser = null;

        assertThrows(IllegalArgumentException.class, () -> {userService.remove(expectedUser);});

        verify(userRepository, never()).remove(expectedUser);
    }

    @Test
    void givenExistingId_whenGetById_shouldReturnUser() {
        Long existingId = 2L;
        User existingUser = new User("Fred", "Weills", "fred@jamb.com", LocalDate.now().minusYears(30).minusDays(30));

        when(userRepository.getById(existingId)).thenReturn(Optional.of(existingUser));
        User actualUser = userService.getById(existingId);

        assertThat(actualUser, equalTo(existingUser));
        verify(userRepository).getById(existingId);
    }

    @Test
    void givenNonExistingId_whenGetById_shouldThrowException() {
        Long nonExistingId = 223L;

        when(userRepository.getById(nonExistingId)).thenReturn(Optional.empty());
        assertThrows(UserNotFoundException.class, () -> {userService.getById(nonExistingId);});

        verify(userRepository).getById(nonExistingId);
    }

    @Test
    void givenNullId_whenGetById_shouldThrowException() {
        Long nullId = null;

        assertThrows(IllegalArgumentException.class, () -> {userService.getById(nullId);});

        verify(userRepository, never()).getById(nullId);
    }

    @Test
    void whenGetAll () {
        int expectedUsersSize = 3;
        List<User> expectedUsers = createUsers(expectedUsersSize);

        when(userRepository.getAll()).thenReturn(expectedUsers);

        List<User> actualUsers = userRepository.getAll();

        assertThat(actualUsers, equalTo(expectedUsers));
        assertThat(actualUsers.size(), equalTo(expectedUsersSize));
        verify(userRepository).getAll();
    }

    private List<User> createUsers(int userQuantity) {
        List<User> userList = new ArrayList<>();

        for (int i = 0; i < userQuantity; i++) {
            User user = new User("Fred", "Weills" + i, i+ "fred@jamb.com", LocalDate.now().minusYears(30).minusDays(i));
            userList.add(user);
        }
        return userList;
    }
}