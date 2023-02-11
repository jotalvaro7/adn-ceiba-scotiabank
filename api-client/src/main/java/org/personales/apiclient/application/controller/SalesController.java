package org.personales.apiclient.application.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.personales.apiclient.domain.data.BookDto;
import org.personales.apiclient.infrastructure.feign.bookApi.BookApiFeign;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class SalesController {


    private final BookApiFeign bookApiFeign;


    @GetMapping("/listar")
    public ResponseEntity<List<BookDto>> showAllBooks() {
        List<BookDto> allBooks = bookApiFeign.getAllBooks();
        if (allBooks.isEmpty()) {
            log.warn("No se han encontrado libros");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            log.info("Lista de libros: {}", allBooks);
            return new ResponseEntity<>(allBooks, HttpStatus.OK);
        }
    }

}
