package com.Intent.shop.facade;


import com.Intent.shop.dto.ChangePasswordDTO;
import com.Intent.shop.dto.UserDTO;
import org.springframework.data.domain.Page;

public interface UserFacade {

    Page<UserDTO> getAllUsers(int pageNum, int pageSize);

    UserDTO getUserById(Long id);

    UserDTO getUserByUsername(String username);


    UserDTO createUser(UserDTO userDTO);

    UserDTO updateUser(Long id, UserDTO updatedUserDTO);

    UserDTO updateUserPassword(Long id, ChangePasswordDTO changePasswordDTO);

}
