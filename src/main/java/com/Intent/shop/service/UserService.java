package com.Intent.shop.service;


import com.Intent.shop.dto.ChangePasswordDTO;
import com.Intent.shop.model.User;
import org.springframework.data.domain.Page;

public interface UserService {
    Page<User> getAllUsers(int pageNum, int pageSize);

    User getUserById(Long id);

    User getUserByUsername(String username);

    User createUser(User user);

    User updateUser(Long id, User updatedUser);

    User changeUserPassword(Long id, ChangePasswordDTO changePasswordDTO);

}
