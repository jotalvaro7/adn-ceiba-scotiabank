package org.personales.oauthserver.service;

import com.netflix.discovery.converters.Auto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.personales.oauthserver.clients.UsuarioFeignClient;
import org.personales.oauthserver.models.AuthCredentials;
import org.personales.oauthserver.models.Token;
import org.personales.oauthserver.models.UserDb;
import org.personales.oauthserver.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class AuthService {

    @Autowired
    private UsuarioFeignClient client;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private Tracer tracer;

    public Token login (AuthCredentials authCredentials){

        try{
            UserDb userDb = client.findByUsername(authCredentials.getUsername());
            log.info("usuario obtenido: {}", userDb.getUsername());
            if(passwordEncoder.matches(authCredentials.getPassword(), userDb.getPassword())){
                return new Token(jwtProvider.createToken(userDb));
            }else {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
            }
        }catch (Exception e){
            String error = "Error en el login, credenciales incorrectas";
            log.error(error + ": " + e.getMessage());
            //tracer
            Objects.requireNonNull(tracer.currentSpan()).tag("error.mensaje", error + ": " + e.getMessage());
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
    }
}
