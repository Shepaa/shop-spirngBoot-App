package com.Intent.shop.service.impl;


import com.Intent.shop.dto.ChangePasswordDTO;
import com.Intent.shop.exception.IncorrectPasswordException;
import com.Intent.shop.exception.PasswordMismatchException;
import com.Intent.shop.exception.UserNotFoundException;
import com.Intent.shop.model.User;
import com.Intent.shop.repository.UserRepository;
import com.Intent.shop.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static com.Intent.shop.util.ExceptionMessageUtil.*;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Page<User> getAllUsers(int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                Sort.by("id"));
        return userRepository.findAll(pageable);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_BY_ID_ERROR_MESSAGE, id)));
    }

    public User getUserByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_BY_USERNAME_ERROR_MESSAGE, username)));
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(Long id, User updatedUser) {
        User existUser = getUserById(id);
        updatedUser.setId(id);
        updatedUser.setPassword(existUser.getPassword());
        return userRepository.save(updatedUser);
    }

    @Override
    public User changeUserPassword(Long id, ChangePasswordDTO changePasswordDTO) {
        User existUser = getUserById(id);
        String newPassword = changePasswordDTO.getNewPassword();
        checkExistPassword(changePasswordDTO.getCurrentPassword(), existUser.getPassword());
        checkConfirmPassword(newPassword, changePasswordDTO.getConfirmPassword());
        existUser.setPassword(newPassword);
        return userRepository.save(existUser);
    }

    private void checkConfirmPassword(String newPassword, String confirmPassword) {
        if(! newPassword.equals(confirmPassword)){
            throw new PasswordMismatchException(PASSWORD_MISMATCH_ERROR_MESSAGE);
        }
    }

    private void checkExistPassword(String currentPassword, String existPassword) {
        if (! currentPassword.equals(existPassword)) {
            throw new IncorrectPasswordException(INCORRECT_PASSWORD_ERROR_MESSAGE);
        }
    }

}
