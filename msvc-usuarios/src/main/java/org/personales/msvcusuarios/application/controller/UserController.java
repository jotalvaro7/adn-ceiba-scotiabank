package org.personales.msvcusuarios.application.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.personales.msvcusuarios.domain.data.UserDto;
import org.personales.msvcusuarios.domain.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class UserController {

    private final UserServiceImpl userServiceImpl;

    @GetMapping("/listar")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> allUsers = userServiceImpl.getAllUsers();
        if (allUsers.isEmpty()) {
            log.warn("No se han encontrado usuarios");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            log.info("Lista de usuarios: {}", allUsers);
            return new ResponseEntity<>(userServiceImpl.getAllUsers(), HttpStatus.OK);
        }
    }

    @GetMapping("/listar/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId) {
        return userServiceImpl.getUserById(userId)
                .map(userDto -> {
                    log.info("usuario con id: {}", userDto);
                    return new ResponseEntity<>(userDto, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    log.warn("No se ha encontrado usuario con id: {}", userId);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }

    @GetMapping("/listar/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
        return userServiceImpl.getUserByUsername(username)
                .map(userDto -> {
                    log.info("Usuario Obtenido: {}", userDto);
                    return new ResponseEntity<>(userDto, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    log.warn("No se ha encontrado usuario con nombre de usuario {}", username);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> newUser(@RequestBody UserDto newUser) {
        log.info("Creando nuevo usuario: {}", newUser);
        return new ResponseEntity<>(userServiceImpl.addUser(newUser), HttpStatus.CREATED);
    }

    @PutMapping("/edit/{userId}")
    public ResponseEntity<UserDto> editUser(@PathVariable Long userId, @RequestBody UserDto user) {
        return userServiceImpl.updateUser(userId, user)
                .map(userUpdate -> {
                    log.info("Usuario actualizado: {}", userUpdate);
                    return new ResponseEntity<>(userUpdate, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    log.warn("Usuario no encontrado con userId: {}", userId);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });

    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId) {
        if (userServiceImpl.getUserById(userId).isEmpty()) {
            log.warn("Usuario no encontrado con userId: {}", userId);
            return new ResponseEntity<>("User not Found with Id: " + userId, HttpStatus.NOT_FOUND);
        }
        log.info("usuario eliminado con userId: {}", userId);
        userServiceImpl.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
