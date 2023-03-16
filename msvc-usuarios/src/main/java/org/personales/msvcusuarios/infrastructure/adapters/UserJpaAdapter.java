package org.personales.msvcusuarios.infrastructure.adapters;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.personales.msvcusuarios.domain.data.UserDto;
import org.personales.msvcusuarios.domain.ports.spi.UserPersistencePort;
import org.personales.msvcusuarios.infrastructure.entity.Role;
import org.personales.msvcusuarios.infrastructure.entity.User;
import org.personales.msvcusuarios.infrastructure.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@AllArgsConstructor
@Service
public class UserJpaAdapter implements UserPersistencePort {

    private final UserRepository userRepository;
    private final ModelMapper mapper;

    @Override
    @Transactional
    public UserDto addUser(UserDto userDto) {
        User user = mapper.map(userDto, User.class);
        User userSaved = userRepository.save(user);
        return mapper.map(userSaved, UserDto.class);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(user -> {
                    return mapper.map(user, UserDto.class);
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> getUserById(Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    return mapper.map(user, UserDto.class);
                });
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<UserDto> getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(user -> {
                    return mapper.map(user, UserDto.class);
                });
    }

    @Override
    @Transactional
    public Optional<UserDto> updateUser(Long userId, UserDto userDto) {
        return userRepository.findById(userId)
                .map(user -> {
                    user.setUsername(userDto.getUsername());
                    user.setPassword(userDto.getPassword());
                    user.setNombre(userDto.getNombre());
                    user.setApellido(userDto.getApellido());
                    user.setEmail(userDto.getEmail());
                    user.setRoles(userDto.getRoles().stream()
                            .map(roleDto -> {
                                return mapper.map(roleDto, Role.class);
                            }).collect(Collectors.toList()));
                    return mapper.map(userRepository.save(user), UserDto.class);
                });
    }

    @Override
    @Transactional
    public void deleteUserById(Long userId) {
        userRepository.deleteById(userId);
    }
}
