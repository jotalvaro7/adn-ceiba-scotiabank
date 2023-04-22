package org.personales.bookapi.application.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personales.bookapi.domain.data.BookDto;
import org.personales.bookapi.domain.ports.api.BookServicePort;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Base64Utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookControllerTest {

    @Mock
    private BookServicePort bookServicePort;

    @Mock
    private ResourceLoader resourceLoader;

    private BookController bookController;

    @BeforeEach
    void setUp() {
        bookController = new BookController(bookServicePort, resourceLoader);
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
    public void testCargarImagen() throws IOException {
        // Arrange
        Long id = 1L;
        String imageName = "testImage.jpg";
        BookDto book = new BookDto();
        book.setId(id);
        book.setImage(imageName);

        byte[] imageBytes = new byte[]{1, 2, 3, 4};
        InputStream inputStream = new ByteArrayInputStream(imageBytes);
        String expectedEncodedImage = Base64Utils.encodeToString(imageBytes);

        Resource resource = mock(Resource.class);
        when(bookServicePort.getBookById(id)).thenReturn(Optional.of(book));
        when(resourceLoader.getResource("classpath:static/" + imageName)).thenReturn(resource);
        when(resource.getInputStream()).thenReturn(inputStream);

        // Act
        ResponseEntity<String> responseEntity = bookController.cargarImagen(id);

        // Assert
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("text/plain", responseEntity.getHeaders().getContentType().toString());
        assertEquals(expectedEncodedImage, responseEntity.getBody());
    }
}