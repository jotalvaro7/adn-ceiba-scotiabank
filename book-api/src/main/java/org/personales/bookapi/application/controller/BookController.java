package org.personales.bookapi.application.controller;

import org.personales.bookapi.domain.data.BookDto;
import org.personales.bookapi.domain.ports.api.BookServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookController {

    @Autowired
    private BookServicePort bookServicePort;


    @GetMapping("/listar")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        return new ResponseEntity<>(bookServicePort.getBooks(), HttpStatus.OK);
    }

    @GetMapping("/listar/{bookId}")
    public ResponseEntity<BookDto> getBookById(@PathVariable long bookId) {
        return bookServicePort.getBookById(bookId)
                .map(bookDto -> new ResponseEntity<>(bookDto, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/crear")
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {
        return new ResponseEntity<>(bookServicePort.addBook(bookDto), HttpStatus.CREATED);
    }

    @PutMapping("/editar/{bookId}")
    public ResponseEntity<BookDto> updateBook(@PathVariable long bookId, @RequestBody BookDto bookDto) {
        return bookServicePort.updateBook(bookId, bookDto)
                .map(bookUpdate -> new ResponseEntity<>(bookUpdate, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("eliminar/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable long bookId) {
        if(bookServicePort.getBookById(bookId).isEmpty()) {
            return new ResponseEntity<>("Book with Id: " + bookId + " no encontrado" ,HttpStatus.NOT_FOUND);
        } else {
            bookServicePort.deleteBookById(bookId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }


}
