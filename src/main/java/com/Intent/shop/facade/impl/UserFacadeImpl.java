package com.Intent.shop.facade.impl;


import com.Intent.shop.dto.ChangePasswordDTO;
import com.Intent.shop.dto.UserDTO;
import com.Intent.shop.facade.UserFacade;
import com.Intent.shop.mapper.UserMapperUtil;
import com.Intent.shop.model.User;
import com.Intent.shop.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import static com.Intent.shop.mapper.UserMapperUtil.toUser;
import static com.Intent.shop.mapper.UserMapperUtil.toUserDTO;

@Component
@RequiredArgsConstructor
public class UserFacadeImpl implements UserFacade {

    private final UserService userService;

    @Override
    public Page<UserDTO> getAllUsers(int pageNum, int pageSize) {
        Page<User> userPage = userService.getAllUsers(pageNum, pageSize);
        return userPage.map(UserMapperUtil::toUserDTO);
    }

    @Override
    public UserDTO getUserById(Long id) {
        User user = userService.getUserById(id);
        return toUserDTO(user);
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        User user = userService.getUserByUsername(username);
        return toUserDTO(user);
    }

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        User createdUser = userService.createUser(toUser(userDTO));
        return toUserDTO(createdUser);
    }

    @Override
    public UserDTO updateUser(Long id, UserDTO updatedUserDTO) {
        User undatedUser = userService.updateUser(id, toUser(updatedUserDTO));
        return toUserDTO(undatedUser);
    }

    @Override
    public UserDTO updateUserPassword(Long id, ChangePasswordDTO changePasswordDTO) {
        User undatedUser = userService.changeUserPassword(id, changePasswordDTO);
        return toUserDTO(undatedUser);
    }
}
