package org.personales.gatewayserver.security;

//import org.springframework.context.annotation.Bean;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;

//@EnableWebFluxSecurity
//public class SpringSecurityConfig {
//
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
//        return http
//                .authorizeExchange()
//                .pathMatchers("/api/security/**").permitAll()
//                .pathMatchers(HttpMethod.GET,
//                        "/api/v1/books",
//                        "/api/v1/ratings").permitAll()
//                .anyExchange().authenticated()
//                .and().csrf().disable()
//                .build();
//    }
//}
