package org.personales.gatewayserver.security;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
@AllArgsConstructor
public class SpringSecurityConfig {

    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        return http
                .authorizeExchange()
                .pathMatchers(
                        "/api/security/**").permitAll()
                .pathMatchers(
                        HttpMethod.GET,
                        "/books/api/v1/listar",
                        "/ratings/api/v1/listar",
                        "/products/api/v1/listar",
                        "/products/api/v1/listar/{productoId}/cantidad/{cantidad}").hasAnyRole( "ADMIN", "USER")
                .pathMatchers(
                        "/usuarios/api/v1/**",
                        "/ratings/api/v1/**",
                        "/books/api/v1/**").hasAnyRole("ADMIN")
                .anyExchange().authenticated()
                .and().addFilterAt(jwtAuthenticationFilter, SecurityWebFiltersOrder.AUTHENTICATION)
                .csrf().disable()
                .build();
    }
}
