package org.personales.bookapi.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personales.bookapi.domain.data.BookDto;
import org.personales.bookapi.domain.ports.spi.BookPersistencePort;

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

    private BookServiceImpl bookServiceImp;

    @BeforeEach
    void setUp() {
        bookServiceImp = new BookServiceImpl(bookPersistencePort);
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
}