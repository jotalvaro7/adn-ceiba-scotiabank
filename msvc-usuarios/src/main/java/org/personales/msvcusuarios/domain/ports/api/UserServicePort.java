package org.personales.msvcusuarios.domain.ports.api;

import org.personales.msvcusuarios.domain.data.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserServicePort {

    UserDto addUser(UserDto userDto);
    List<UserDto> getAllUsers();
    Optional<UserDto> getUserById(Long id);
    Optional<UserDto> getUserByUsername(String username);
    Optional<UserDto> updateUser(Long id, UserDto userDto);
    void deleteUserById(Long id);


}
