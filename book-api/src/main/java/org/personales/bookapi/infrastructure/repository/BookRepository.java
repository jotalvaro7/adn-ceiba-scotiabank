package org.personales.bookapi.infrastructure.repository;

import org.personales.bookapi.infrastructure.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BookRepository extends JpaRepository<Book, Long> {


}
