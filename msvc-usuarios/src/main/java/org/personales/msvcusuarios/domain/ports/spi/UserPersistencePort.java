package org.personales.msvcusuarios.domain.ports.spi;

import org.personales.msvcusuarios.domain.data.UserDto;

import java.util.List;
import java.util.Optional;

public interface UserPersistencePort {

    UserDto addUser(UserDto userDto);
    List<UserDto> getAllUsers();
    Optional<UserDto> getUserById(Long id);
    Optional<UserDto> getUserByUsername(String username);
    Optional<UserDto> updateUser(Long id, UserDto userDto);
    void deleteUserById(Long id);

}
