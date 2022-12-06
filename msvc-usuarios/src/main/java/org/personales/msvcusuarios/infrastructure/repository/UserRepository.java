package org.personales.msvcusuarios.infrastructure.repository;

import org.personales.msvcusuarios.infrastructure.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

}
