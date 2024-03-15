package com.intent.BookStore.unit.service;

import com.Intent.shop.exception.UserNotFoundException;
import com.Intent.shop.model.User;
import com.Intent.shop.repository.UserRepository;
import com.Intent.shop.service.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Optional;
import static com.Intent.shop.util.ExceptionMessageUtil.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void getUserByUsernameTest() {
        String username = "testuser";
        User user = new User()
                .setId(1L)
                .setUsername(username)
                .setPassword("testpassword")
                .setEmail("test@example.com")
                .setPhoneNumber("1234567890");
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        User result = userService.getUserByUsername(username);

        assertEquals(user, result);
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void getUserByUsernameWithNotFoundExceptionTest() {
        String username = "nonexistentuser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> userService.getUserByUsername(username));

        assertEquals(String.format(USER_NOT_FOUND_BY_USERNAME_ERROR_MESSAGE, username), exception.getMessage());
        verify(userRepository, times(1)).findByUsername(username);
    }

    @Test
    void createUserTest() {
        User user = new User()
                .setId(1L)
                .setUsername("newuser")
                .setPassword("newpassword")
                .setEmail("newuser@example.com")
                .setPhoneNumber("9876543210");
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.createUser(user);

        assertEquals(user, result);
        verify(userRepository, times(1)).save(user);
    }

}
