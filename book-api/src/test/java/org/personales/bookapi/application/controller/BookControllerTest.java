package org.personales.bookapi.application.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personales.bookapi.domain.data.BookDto;
import org.personales.bookapi.domain.ports.api.BookServicePort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookServicePort bookServicePort;

    private BookController bookController;

    @BeforeEach
    void setUp() {
        bookController = new BookController(bookServicePort);
    }

    @Test
    void getAllBooks_ShouldReturn200AndListOfBooks_WhenBooksAreFound() {
        List<BookDto> allBooks = Arrays.asList(
                new BookDto(1L, "Title 1", "Author 1", 1000d, "url1", 1010),
                new BookDto(2L, "Title 2", "Author 2", 2000d, "url2", 1010)
        );

        when(bookServicePort.getBooks()).thenReturn(allBooks);

        ResponseEntity<List<BookDto>> response = bookController.getAllBooks();

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), allBooks);
        verify(bookServicePort).getBooks();

    }

    @Test
    void getAllBooks_ShouldReturn404_WhenBooksNotFound() {
        when(bookServicePort.getBooks()).thenReturn(Collections.emptyList());

        ResponseEntity<List<BookDto>> response = bookController.getAllBooks();

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        verify(bookServicePort).getBooks();

    }

    @Test
    void getBookById_ShouldReturn200AndBook_WhenBookByIdIsFound() {
        Long bookId = 1L;
        BookDto bookDto = new BookDto(1L, "Title 1", "Author 1", 1000d, "url1", 1010);

        when(bookServicePort.getBookById(bookId)).thenReturn(Optional.of(bookDto));

        ResponseEntity<BookDto> response = bookController.getBookById(bookId);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), bookDto);

    }

    @Test
    void getBookById_ShouldReturn400_WhenBookByIdNotFound() {
        Long bookId = 1L;
        Optional<BookDto> optionalBookDto = Optional.empty();

        when(bookServicePort.getBookById(bookId)).thenReturn(optionalBookDto);

        ResponseEntity<BookDto> response = bookController.getBookById(bookId);

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        verify(bookServicePort).getBookById(bookId);
    }

    @Test
    void addBook_ShouldReturnBook_whenBookIsAdd() {
        BookDto bookDto = new BookDto(1L, "Title 1", "Author 1", 1000d, "url1", 1010);

        when(bookServicePort.addBook(bookDto)).thenReturn(bookDto);

        ResponseEntity<BookDto> response = bookController.addBook(bookDto);

        assertEquals(response.getStatusCode(), HttpStatus.CREATED);
        verify(bookServicePort).addBook(bookDto);
    }

    @Test
    void updateBook_ShouldReturn200AndBookUpdate_WhenBookIsFound() {
        Long bookId = 1L;
        BookDto bookUpdateDto = new BookDto(1L, "Title 1", "Author 1", 1000d, "url1", 1010);

        when(bookServicePort.updateBook(bookId, bookUpdateDto)).thenReturn(Optional.of(bookUpdateDto));

        ResponseEntity<BookDto> response = bookController.updateBook(bookId, bookUpdateDto);

        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(response.getBody(), bookUpdateDto);
        verify(bookServicePort).updateBook(bookId, bookUpdateDto);
    }

    @Test
    void updateBook_ShouldReturn404_WhenBookNotFound() {
        Long bookId = 1L;

        Optional<BookDto> optionalBookDto = Optional.empty();

        when(bookServicePort.updateBook(bookId, new BookDto())).thenReturn(optionalBookDto);

        ResponseEntity<BookDto> response = bookController.updateBook(bookId, new BookDto());

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        verify(bookServicePort).updateBook(bookId, new BookDto());

    }

    @Test
    void deleteBook_ShouldReturn204_WhenBookIsFound() {
        Long bookId = 1L;
        Optional<BookDto> optionalBookDto = Optional.of(new BookDto());

        when(bookServicePort.getBookById(bookId)).thenReturn(optionalBookDto);

        ResponseEntity<?> response = bookController.deleteBook(bookId);

        assertEquals(response.getStatusCode(), HttpStatus.NO_CONTENT);
        verify(bookServicePort).getBookById(bookId);
        verify(bookServicePort).deleteBookById(bookId);

    }

    @Test
    void deleteBook_ShouldReturn404_WhenBookNotFound() {
        Long bookId = 1L;
        Optional<BookDto> optionalBookDto = Optional.empty();

        when(bookServicePort.getBookById(bookId)).thenReturn(optionalBookDto);

        ResponseEntity<?> response = bookController.deleteBook(bookId);

        assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
        verify(bookServicePort).getBookById(bookId);

    }

    @Test
    void getImage_ShouldReturnCodedImage_WhenTheImageExist() throws IOException {
        Long id = 1L;
        String expectedEncodedImage = "encoded-image";

        when(bookServicePort.getImage(id)).thenReturn(expectedEncodedImage);

        ResponseEntity<String> response = bookController.obtenerImagen(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("text/plain", Objects.requireNonNull(response.getHeaders().getContentType()).toString());
        assertEquals(expectedEncodedImage, response.getBody());
    }

    @Test
    void obtenerImagenNotFound() throws IOException {
        Long id = 1L;
        when(bookServicePort.getImage(id)).thenReturn(null);

        ResponseEntity<String> response = bookController.obtenerImagen(id);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}