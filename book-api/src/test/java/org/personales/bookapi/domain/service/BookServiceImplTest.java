package org.personales.bookapi.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personales.bookapi.domain.data.BookDto;
import org.personales.bookapi.domain.ports.spi.BookPersistencePort;
import org.personales.bookapi.infrastructure.utils.IOUtilsWrapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {

    @Mock
    private BookPersistencePort bookPersistencePort;

    @Mock
    private ResourceLoader resourceLoader;

    @Mock
    private IOUtilsWrapper ioUtilsWrapper;

    private BookServiceImpl bookServiceImp;

    @BeforeEach
    void setUp() {
        bookServiceImp = new BookServiceImpl(bookPersistencePort, resourceLoader, ioUtilsWrapper);
    }

    @Test
    void addBookTest() {
        BookDto bookDto = new BookDto();
        when(bookPersistencePort.addBook(bookDto)).thenReturn(bookDto);

        BookDto result = bookServiceImp.addBook(bookDto);

        verify(bookPersistencePort).addBook(bookDto);
        assertEquals(bookDto, result);
    }

    @Test
    void deleteBookByIdTest() {
        Long id = 1L;

        bookServiceImp.deleteBookById(id);

        verify(bookPersistencePort).deleteBookById(id);
    }

    @Test
    void updateBookTest() {
        Long id = 1L;
        BookDto bookDto = new BookDto();
        Optional<BookDto> expected = Optional.of(bookDto);
        when(bookPersistencePort.updateBook(id, bookDto)).thenReturn(expected);

        Optional<BookDto> result = bookServiceImp.updateBook(id, bookDto);

        verify(bookPersistencePort).updateBook(id, bookDto);
        assertEquals(expected, result);

    }

    @Test
    void getBookByIdTest() {
        Long bookId = 1L;
        BookDto bookDto = new BookDto();
        Optional<BookDto> expected = Optional.of(bookDto);
        when(bookPersistencePort.getBookById(bookId)).thenReturn(expected);

        Optional<BookDto> result = bookServiceImp.getBookById(bookId);

        verify(bookPersistencePort).getBookById(bookId);
        assertEquals(expected, result);


    }

    @Test
    void getBooksTest() {
        List<BookDto> expected = new ArrayList<>();
        when(bookPersistencePort.getBooks()).thenReturn(expected);

        List<BookDto> result = bookServiceImp.getBooks();

        verify(bookPersistencePort).getBooks();
        assertEquals(expected, result);
    }

    @Test
    void getImageSuccess() throws IOException {
        Long id = 1L;
        String imageFileName = "image.png";
        BookDto bookDto = new BookDto(id, "Title", "Author", 3000.00, imageFileName, 2000);
        Optional<BookDto> bookOptional = Optional.of(bookDto);

        Resource resource = Mockito.mock(Resource.class);
        InputStream inputStream = Mockito.mock(InputStream.class);

        byte[] imageBytes = new byte[]{1, 2, 3};
        String expectedEncodedImage = Base64Utils.encodeToString(imageBytes);

        when(bookPersistencePort.getBookById(id)).thenReturn(bookOptional);
        when(resourceLoader.getResource("classpath:static/" + imageFileName)).thenReturn(resource);
        when(resource.getInputStream()).thenReturn(inputStream);
        when(ioUtilsWrapper.toByteArray(inputStream)).thenReturn(imageBytes);

        String actualEncodedImage = bookServiceImp.getImage(id);

        assertEquals(expectedEncodedImage, actualEncodedImage);

    }

    @Test
    void getImageNotFound() throws IOException {
        Long id = 1L;
        when(bookPersistencePort.getBookById(id)).thenReturn(Optional.empty());

        String actualEncodedImage = bookServiceImp.getImage(id);

        assertNull(actualEncodedImage);
    }
}