package org.personales.msvcusuarios.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personales.msvcusuarios.domain.data.UserDto;
import org.personales.msvcusuarios.domain.ports.spi.UserPersistencePort;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserPersistencePort userPersistencePort;

    private UserServiceImpl userServiceImp;

    @BeforeEach
    void setUp() {
        userServiceImp = new UserServiceImpl(userPersistencePort);
    }

    @Test
    void addUserTest() {
        UserDto userDto = new UserDto();

        when(userPersistencePort.addUser(userDto)).thenReturn(userDto);

        UserDto result = userServiceImp.addUser(userDto);

        assertEquals(userDto, result);
        verify(userPersistencePort).addUser(userDto);

    }

    @Test
    void getAllUserTest() {
        UserDto user1 = new UserDto();
        UserDto user2 = new UserDto();
        List<UserDto> users = Arrays.asList(user1, user2);

        when(userPersistencePort.getAllUsers()).thenReturn(users);

        List<UserDto> response = userServiceImp.getAllUsers();

        assertEquals(users, response);
        verify(userPersistencePort).getAllUsers();

    }

    @Test
    void getUserByIdTest() {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        Optional<UserDto> userDtoOptional = Optional.of(userDto);

        when(userPersistencePort.getUserById(userId)).thenReturn(userDtoOptional);

        Optional<UserDto> result = userServiceImp.getUserById(userId);

        assertEquals(userDtoOptional, result);
        verify(userPersistencePort).getUserById(userId);

    }

    @Test
    void getUserByUserNameTest(){
        String userName = "Jhon";
        UserDto userDto = new UserDto();
        Optional<UserDto> userDtoOptional = Optional.of(userDto);

        when(userPersistencePort.getUserByUsername(userName)).thenReturn(userDtoOptional);

        Optional<UserDto> result = userServiceImp.getUserByUsername(userName);

        assertEquals(userDtoOptional, result);
        verify(userPersistencePort).getUserByUsername(userName);

    }

    @Test
    void updateUserTest(){
        Long userId = 1L;
        UserDto userDto = new UserDto();
        Optional<UserDto> userDtoOptional = Optional.of(userDto);

        when(userPersistencePort.updateUser(userId, userDto)).thenReturn(userDtoOptional);

        Optional<UserDto> result = userServiceImp.updateUser(userId, userDto);

        assertEquals(userDtoOptional, result);
        verify(userPersistencePort).updateUser(userId, userDto);
    }

    @Test
    void deleteUserByIdTest(){
        Long userId = 1L;

        userServiceImp.deleteUserById(userId);

        verify(userPersistencePort).deleteUserById(userId);


    }
}