package org.personales.oauthserver.service;

import com.netflix.discovery.converters.Auto;
import lombok.extern.slf4j.Slf4j;
import org.personales.oauthserver.clients.UsuarioFeignClient;
import org.personales.oauthserver.models.AuthCredentials;
import org.personales.oauthserver.models.Token;
import org.personales.oauthserver.models.UserDb;
import org.personales.oauthserver.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Slf4j
@Service
public class AuthService {

    @Autowired
    private UsuarioFeignClient client;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Token login (AuthCredentials authCredentials){

        try{
            UserDb userDb = client.findByUsername(authCredentials.getUsername());
            if(passwordEncoder.matches(authCredentials.getPassword(), userDb.getPassword())){
                return new Token(jwtProvider.createToken(userDb));
            }else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            String error = "Error en el login, credenciales incorrectas";
            log.error(error);
            //tracer
            throw  new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
