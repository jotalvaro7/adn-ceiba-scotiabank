package org.personales.msvcusuarios.infrastructure.adapters;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.personales.msvcusuarios.domain.data.UserDto;
import org.personales.msvcusuarios.domain.ports.spi.UserPersistencePort;
import org.personales.msvcusuarios.infrastructure.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserJpaAdapter implements UserPersistencePort {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional
    public UserDto addUser(UserDto userDto) {
        return null;
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
    public Optional<UserDto> getUserById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDto> getUserByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public Optional<UserDto> updateUser(Long id, UserDto userDto) {
        return Optional.empty();
    }

    @Override
    public void deleteUserById(Long id) {

    }
}
