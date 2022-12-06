package org.personales.msvcusuarios.application.controller;

import org.personales.msvcusuarios.domain.data.UserDto;
import org.personales.msvcusuarios.domain.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/listar")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return new ResponseEntity<>(userServiceImpl.getAllUsers(), HttpStatus.OK);
    }
}
