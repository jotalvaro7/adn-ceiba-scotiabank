package org.personales.msvcusuarios.domain.service;

import lombok.RequiredArgsConstructor;
import org.personales.msvcusuarios.domain.data.UserDto;
import org.personales.msvcusuarios.domain.ports.api.UserServicePort;
import org.personales.msvcusuarios.domain.ports.spi.UserPersistencePort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserServicePort {

    private final UserPersistencePort userPersistencePort;

    @Override
    public UserDto addUser(UserDto userDto) {
        return userPersistencePort.addUser(userDto);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userPersistencePort.getAllUsers();
    }

    @Override
    public Optional<UserDto> getUserById(Long id) {
        return userPersistencePort.getUserById(id);
    }

    @Override
    public Optional<UserDto> getUserByUsername(String username) {
        return userPersistencePort.getUserByUsername(username);
    }

    @Override
    public Optional<UserDto> updateUser(Long id, UserDto userDto) {
        return userPersistencePort.updateUser(id, userDto);
    }

    @Override
    public void deleteUserById(Long id) {
        userPersistencePort.deleteUserById(id);
    }
}
