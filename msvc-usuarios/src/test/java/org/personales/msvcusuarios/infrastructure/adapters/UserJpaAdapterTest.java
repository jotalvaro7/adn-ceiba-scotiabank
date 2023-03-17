package org.personales.msvcusuarios.infrastructure.adapters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.personales.msvcusuarios.domain.data.RoleDto;
import org.personales.msvcusuarios.domain.data.UserDto;
import org.personales.msvcusuarios.infrastructure.entity.Role;
import org.personales.msvcusuarios.infrastructure.entity.User;
import org.personales.msvcusuarios.infrastructure.repository.UserRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserJpaAdapterTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper mapper;

    private UserJpaAdapter userJpaAdapter;

    private User user;
    private UserDto userDto;
    private Role role;
    private RoleDto roleDto;

    @BeforeEach
    void setUp() {
        userJpaAdapter = new UserJpaAdapter(userRepository, mapper);

        role = new Role(1L, "ROLE_USER");
        roleDto = new RoleDto(1L, "ROLE_USER");
        user = new User(1L, "username", "password", true, "Jhon", "Doe", "test@mail.com", Collections.singletonList(role));
        userDto = new UserDto(1L, "username", "password", true, "Jhon", "Doe", "test@mail.com", Collections.singletonList(roleDto));

    }

    @Test
    void addUserTest() {

        User userSaved = new User();

        when(mapper.map(userDto, User.class)).thenReturn(user);
        when(userRepository.save(user)).thenReturn(userSaved);
        when(mapper.map(userSaved, UserDto.class)).thenReturn(userDto);

        UserDto result = userJpaAdapter.addUser(userDto);

        assertEquals(userDto, result);
        verify(mapper).map(userDto, User.class);
        verify(userRepository).save(user);
        verify(mapper).map(userSaved, UserDto.class);

    }

    @Test
    void getAllUsersTest() {
        List<User> users = Arrays.asList(new User(), new User());
        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(new UserDto());
        userDtos.add(new UserDto());

        when(userRepository.findAll()).thenReturn(users);
        when(mapper.map(any(User.class), eq(UserDto.class))).thenReturn(userDtos.get(0));

        List<UserDto> result = userJpaAdapter.getAllUsers();

        assertEquals(userDtos.get(0), result.get(0));
        verify(userRepository).findAll();
    }

    @Test
    void getUserByIdTest() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(mapper.map(user, UserDto.class)).thenReturn(userDto);

        Optional<UserDto> result = userJpaAdapter.getUserById(userId);

        assertTrue(result.isPresent());
        assertEquals(userDto, result.get());
        verify(userRepository).findById(userId);
    }

    @Test
    void getUserByUserNameTest() {
        String userName = "Jhon";


        when(userRepository.findByUsername(userName)).thenReturn(Optional.of(user));
        when(mapper.map(user, UserDto.class)).thenReturn(userDto);

        Optional<UserDto> result = userJpaAdapter.getUserByUsername(userName);

        assertTrue(result.isPresent());
        assertEquals(userDto, result.get());
        verify(userRepository).findByUsername(userName);
    }

    @Test
    void updateUserTest() {
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        when(mapper.map(roleDto, Role.class)).thenReturn(role);
        when(mapper.map(user, UserDto.class)).thenReturn(userDto);

        Optional<UserDto> result = userJpaAdapter.updateUser(userId, userDto);

        assertTrue(result.isPresent());
        assertEquals(userDto, result.get());
        verify(userRepository).findById(1L);
        verify(userRepository).save(user);
        verify(mapper, times(1)).map(roleDto, Role.class);
        verify(mapper).map(user, UserDto.class);

    }

    @Test
    void deleteUserByIdTest() {
        Long userId = 1L;

        userJpaAdapter.deleteUserById(userId);

        verify(userRepository).deleteById(userId);
    }


}