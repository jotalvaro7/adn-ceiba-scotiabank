package org.personales.msvcusuarios.application.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personales.msvcusuarios.domain.data.UserDto;
import org.personales.msvcusuarios.domain.service.UserServiceImpl;
import org.personales.msvcusuarios.infrastructure.entity.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserServiceImpl userServiceImpl;

    private UserController userController;

    @BeforeEach
    void setUp() {
        userController = new UserController(userServiceImpl);
    }

    @Test
    void getAllUsers_ShouldReturn200AndAllUsers_WhenUsersAreFound() {

        UserDto user1 = new UserDto();
        UserDto user2 = new UserDto();
        List<UserDto> users = Arrays.asList(user1, user2);
        when(userServiceImpl.getAllUsers()).thenReturn(users);

        ResponseEntity<List<UserDto>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
        assertEquals(users, response.getBody());

    }

    @Test
    void getAllUsers_ShoulReturn404_WhenUsersNotFound() {
        when(userServiceImpl.getAllUsers()).thenReturn(Collections.emptyList());

        ResponseEntity<List<UserDto>> response = userController.getAllUsers();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(userServiceImpl).getAllUsers();
    }

    @Test
    void getUserById_ShouldReturn200AndUser_WhenUserIsFound() {
        Long userId = 1L;
        UserDto userDto = new UserDto();

        when(userServiceImpl.getUserById(1L)).thenReturn(Optional.of(userDto));

        ResponseEntity<?> result = userController.getUserById(userId);

        assertEquals(userDto, result.getBody());
        assertEquals(HttpStatus.OK, result.getStatusCode());

    }

    @Test
    void getUserId_ShouldReturn404_WhenUserNotFound() {
        Long userId = 1L;
        Optional<UserDto> optionalUserDto = Optional.empty();
        when(userServiceImpl.getUserById(userId)).thenReturn(optionalUserDto);

        ResponseEntity<?> result = userController.getUserById(userId);

        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(userServiceImpl).getUserById(userId);

    }

    @Test
    void getUserByUserName_ShouldReturn200AndUser_whenUserIsFound() {
        String userName = "Jhon";
        UserDto userDto = new UserDto();

        when(userServiceImpl.getUserByUsername(userName)).thenReturn(Optional.of(userDto));

        ResponseEntity<?> reponse = userController.getUserByUsername(userName);

        assertEquals(HttpStatus.OK, reponse.getStatusCode());
        assertEquals(userDto, reponse.getBody());
        verify(userServiceImpl).getUserByUsername(userName);


    }

    @Test
    void getUserByUserName_ShouldReturn404_whenUserNotFound() {
        String userName = "Jhon";

        when(userServiceImpl.getUserByUsername(userName)).thenReturn(Optional.empty());

        ResponseEntity<?> reponse = userController.getUserByUsername(userName);

        assertEquals(HttpStatus.NOT_FOUND, reponse.getStatusCode());
        verify(userServiceImpl).getUserByUsername(userName);

    }

    @Test
    void createNewUser() {
        UserDto newUser = new UserDto();

        when(userServiceImpl.addUser(newUser)).thenReturn(newUser);

        ResponseEntity<UserDto> response = userController.newUser(newUser);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(newUser, response.getBody());
        verify(userServiceImpl).addUser(newUser);
    }

    @Test
    void editUser_ShouldReturn200AndUserEdit_WhenUserIsFound() {
        Long userId = 1L;
        UserDto userDto = new UserDto();
        UserDto userUpdate = new UserDto();

        when(userServiceImpl.updateUser(userId, userDto)).thenReturn(Optional.of(userUpdate));

        ResponseEntity<UserDto> response = userController.editUser(userId, userDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userUpdate, response.getBody());
        verify(userServiceImpl).updateUser(userId, userDto);

    }

    @Test
    void editUser_ShouldReturn404_WhenUserNotFound() {
        Long userId = 1L;
        UserDto userDto = new UserDto();

        when(userServiceImpl.updateUser(userId, userDto)).thenReturn(Optional.empty());

        ResponseEntity<UserDto> response = userController.editUser(userId, userDto);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        verify(userServiceImpl).updateUser(userId, userDto);

    }

    @Test
    void deleteUser_ShouldReturn202_WhenUserIsFound() {
        Long userId = 1L;
        UserDto userDto = new UserDto();

        when(userServiceImpl.getUserById(userId)).thenReturn(Optional.of(userDto));

        ResponseEntity<?> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(userServiceImpl).deleteUserById(userId);

    }

    @Test
    void deleteUser_ShouldReturn404_WhenDeletingNotUserFound() {
        Long userId = 1L;

        when(userServiceImpl.getUserById(userId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = userController.deleteUser(userId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User not Found with Id: " + userId, response.getBody());
        verify(userServiceImpl, never()).deleteUserById(userId);

    }

}