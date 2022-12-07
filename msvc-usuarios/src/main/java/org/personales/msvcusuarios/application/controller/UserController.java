package org.personales.msvcusuarios.application.controller;

import org.personales.msvcusuarios.domain.data.UserDto;
import org.personales.msvcusuarios.domain.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/listar")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return new ResponseEntity<>(userServiceImpl.getAllUsers(), HttpStatus.OK);
    }

    @GetMapping("/listar/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId){
        return userServiceImpl.getUserById(userId)
                .map(userDto -> new ResponseEntity<>(userDto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/listar/username/{username}")
    public ResponseEntity<?> getUserByUsername(@PathVariable String username){
        return userServiceImpl.getUserByUsername(username)
                .map(userDto -> new ResponseEntity<>(userDto, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/create")
    public ResponseEntity<UserDto> newUser(@RequestBody UserDto newUser){
        return new ResponseEntity<>(userServiceImpl.addUser(newUser), HttpStatus.CREATED);
    }

    @PutMapping("/edit/{userId}")
    public ResponseEntity<UserDto> editUser(@PathVariable Long userId, @RequestBody UserDto user){
        return userServiceImpl.updateUser(userId, user)
                .map(userUpdate -> new ResponseEntity<>(userUpdate, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable Long userId){
        if(userServiceImpl.getUserById(userId).isEmpty()){
            return new ResponseEntity<>("User not Found with Id: " + userId, HttpStatus.NOT_FOUND);
        }
        userServiceImpl.deleteUserById(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
