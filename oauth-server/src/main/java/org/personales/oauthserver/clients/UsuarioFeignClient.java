package org.personales.oauthserver.clients;


import org.personales.oauthserver.models.UserDb;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "msvc-usuarios")
public interface UsuarioFeignClient {

    @GetMapping("/listar/username/{username}")
    UserDb findByUsername (@RequestParam String username);

}
