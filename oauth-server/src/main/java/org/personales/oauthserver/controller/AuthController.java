package org.personales.oauthserver.controller;

import org.personales.oauthserver.models.AuthCredentials;
import org.personales.oauthserver.models.Token;
import org.personales.oauthserver.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
public class AuthController {


    @Autowired
    private AuthService authService;


    @PostMapping("/login")
    public ResponseEntity<Token> login(@RequestBody AuthCredentials authCredentials){
        return ResponseEntity.ok(authService.login(authCredentials));
    }

}
