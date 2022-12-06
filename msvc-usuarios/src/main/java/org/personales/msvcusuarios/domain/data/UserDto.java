package org.personales.msvcusuarios.domain.data;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long id;
    private String username;
    private String password;
    private Boolean enabled;
    private String nombre;
    private String apellido;
    private String email;
    private Collection<RoleDto> roles;
}
