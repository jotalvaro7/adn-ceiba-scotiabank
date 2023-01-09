package org.personales.oauthserver.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.personales.oauthserver.models.Role;
import org.personales.oauthserver.models.UserDb;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RefreshScope
@Component
public class JwtProvider {

    @Autowired
    private Environment env;

    public String createToken(UserDb userDb){
        return Jwts.builder()
                .setClaims(setClaims(userDb))
                .setIssuedAt(expeditionDate())
                .setExpiration(expirationDate())
                .signWith(getSecretKey())
                .compact();
    }

    private Map<String, Object> setClaims(UserDb userDb){
        Map<String, Object> claims = Jwts.claims().setSubject(userDb.getUsername());
        claims.put("nombre", userDb.getNombre());
        claims.put("apellido", userDb.getApellido());
        claims.put("email", userDb.getEmail());
        claims.put("roles", getRolesWithOutId(userDb));
        return claims;
    }

    private Date expeditionDate(){
        return new Date();
    }

    private Date expirationDate(){
        return new Date(expeditionDate().getTime() + 3600 * 1000);
    }

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(env.getProperty("config.security.oauth.jwt.key").getBytes());
    }

    private List<String> getRolesWithOutId(UserDb userDb){
        return userDb.getRoles().stream().map(Role::getNombre).collect(Collectors.toList());
    }

}
