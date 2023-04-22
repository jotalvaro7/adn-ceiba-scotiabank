package org.personales.bookapi.application.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.personales.bookapi.domain.data.BookDto;
import org.personales.bookapi.domain.ports.api.BookServicePort;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@AllArgsConstructor
public class BookController {

    private final BookServicePort bookServicePort;

    private final ResourceLoader resourceLoader;

    @GetMapping("/listar")
    public ResponseEntity<List<BookDto>> getAllBooks() {
        List<BookDto> allBooks = bookServicePort.getBooks();
        if (allBooks.isEmpty()) {
            log.warn("No se han encontrado libros");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            log.info("Lista de libros: {}", allBooks);
            return new ResponseEntity<>(allBooks, HttpStatus.OK);
        }
    }

    @GetMapping("/listar/{bookId}")
    public ResponseEntity<BookDto> getBookById(@PathVariable long bookId) {
        return bookServicePort.getBookById(bookId)
                .map(bookDto -> {
                    log.info("Libro con id: {} encontrado: {}", bookId, bookDto);
                    return new ResponseEntity<>(bookDto, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    log.warn("No se ha encontrado ningun libro con bookId: {}", bookId);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }

    @PostMapping("/crear")
    public ResponseEntity<BookDto> addBook(@RequestBody BookDto bookDto) {
        log.info("Creando nuevo libro: {}", bookDto);
        return new ResponseEntity<>(bookServicePort.addBook(bookDto), HttpStatus.CREATED);
    }

    @PutMapping("/editar/{bookId}")
    public ResponseEntity<BookDto> updateBook(@PathVariable long bookId, @RequestBody BookDto bookDto) {
        return bookServicePort.updateBook(bookId, bookDto)
                .map(bookUpdate -> {
                    log.info("Libro actualizado: {}", bookUpdate);
                    return new ResponseEntity<>(bookUpdate, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    log.warn("Libro no encontrado con bookId: {}", bookId);
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                });
    }

    @DeleteMapping("eliminar/{bookId}")
    public ResponseEntity<?> deleteBook(@PathVariable long bookId) {
        if (bookServicePort.getBookById(bookId).isEmpty()) {
            log.warn("Libro no encontrado con bookId: {}", bookId);
            return new ResponseEntity<>("Book with Id: " + bookId + " no encontrado", HttpStatus.NOT_FOUND);
        } else {
            log.info("Libro eliminado con bookId: {}", bookId);
            bookServicePort.deleteBookById(bookId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @GetMapping("/image/{id}")
    public ResponseEntity<String> cargarImagen(@PathVariable("id") Long id) throws IOException {
        Optional<BookDto> book = bookServicePort.getBookById(id);
        Resource resource = resourceLoader.getResource("classpath:static/" + book.get().getImage());
        InputStream inputStream = resource.getInputStream();
        byte[] imagen = IOUtils.toByteArray(inputStream);
        String encodedImage = Base64Utils.encodeToString(imagen);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "text/plain");
        return new ResponseEntity<>(encodedImage, headers, HttpStatus.OK);
    }


}
