package com.intent.BookStore.unit.facade;

import com.Intent.shop.dto.UserDTO;
import com.Intent.shop.facade.impl.UserFacadeImpl;
import com.Intent.shop.model.User;
import com.Intent.shop.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserFacadeImplTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserFacadeImpl userFacade;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllUsers() {
        int pageNum = 0;
        int pageSize = 10;
        User user1 = new User(1L, "user1", "password1", "user1@example.com", "+380123456789", null);
        User user2 = new User(2L, "user2", "password2", "user2@example.com", "+380987654321", null);
        List<User> users = Arrays.asList(user1, user2);
        Page<User> userPage = new PageImpl<>(users, PageRequest.of(pageNum, pageSize), users.size());
        when(userService.getAllUsers(pageNum, pageSize)).thenReturn(userPage);

        Page<UserDTO> result = userFacade.getAllUsers(pageNum, pageSize);

        verify(userService, times(1)).getAllUsers(pageNum, pageSize);
        assertEquals(2, result.getContent().size());
    }

    @Test
    void getUserById() {
        Long userId = 1L;
        User user = new User(userId, "user1", "password1", "user1@example.com", "+380123456789", null);
        when(userService.getUserById(userId)).thenReturn(user);

        UserDTO result = userFacade.getUserById(userId);

        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void getUserByUsername() {
        String username = "user1";
        User user = new User(1L, username, "password1", "user1@example.com", "+380123456789", null);
        when(userService.getUserByUsername(username)).thenReturn(user);

        UserDTO result = userFacade.getUserByUsername(username);

        verify(userService, times(1)).getUserByUsername(username);
    }

    @Test
    void createUser() {
        UserDTO userDTO = new UserDTO(null, "newUser", "password123", "newuser@example.com", "+380987654321", null);
        User user = new User(1L, "newUser", "password123", "newuser@example.com", "+380987654321", null);
        when(userService.createUser(any(User.class))).thenReturn(user);

        UserDTO result = userFacade.createUser(userDTO);

        verify(userService, times(1)).createUser(any(User.class));
    }
}