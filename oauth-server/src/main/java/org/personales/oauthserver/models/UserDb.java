package org.personales.oauthserver.models;

import lombok.Data;

import java.util.Collection;

@Data
public class UserDb {

    private Long id;
    private String username;
    private String password;
    private Boolean enabled;
    private String nombre;
    private String apellido;
    private String email;
    private Collection<Role> roles;

}
