package org.personales.bookapi.infrastructure.adapters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.personales.bookapi.domain.data.BookDto;
import org.personales.bookapi.infrastructure.entity.Book;
import org.personales.bookapi.infrastructure.repository.BookRepository;
import org.springframework.core.env.Environment;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BookJpaAdapterTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private Environment env;

    private BookJpaAdapter bookJpaAdapter;

    @BeforeEach
    void setUp() {
        bookJpaAdapter = new BookJpaAdapter(bookRepository, modelMapper, env);
    }

    @Test
    void addBookTest() {
        BookDto bookDto = new BookDto();
        Book book = new Book();
        Book bookSaved = new Book();

        when(modelMapper.map(bookDto, Book.class)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(bookSaved);
        when(modelMapper.map(bookSaved, BookDto.class)).thenReturn(bookDto);

        BookDto result = bookJpaAdapter.addBook(bookDto);

        assertEquals(result, bookDto);
        verify(modelMapper).map(bookDto, Book.class);
        verify(bookRepository).save(book);
        verify(modelMapper).map(bookSaved, BookDto.class);
    }

    @Test
    void deleteBookIdTest() {
        Long id = 1L;

        bookJpaAdapter.deleteBookById(id);

        verify(bookRepository).deleteById(id);
    }

    @Test
    void updateBookTest() {
        Long id = 1L;
        BookDto bookDto = new BookDto();
        Book book = new Book();
        Book bookSaved = new Book();


        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(bookRepository.save(book)).thenReturn(bookSaved);
        when(modelMapper.map(bookSaved, BookDto.class)).thenReturn(bookDto);

        Optional<BookDto> response = bookJpaAdapter.updateBook(id, bookDto);

        assertEquals(response.get(), bookDto);
        verify(bookRepository).findById(id);
        verify(bookRepository).save(book);

    }

    @Test
    void getBooksTest() {
        List<Book> books = Arrays.asList(new Book(), new Book());
        List<BookDto> booksDto = new ArrayList<>();
        booksDto.add(new BookDto());
        booksDto.add(new BookDto());
        int port = 8080;

        when(bookRepository.findAll()).thenReturn(books);
        when(modelMapper.map(any(Book.class), eq(BookDto.class))).thenReturn(booksDto.get(0));
        when(env.getProperty("local.server.port")).thenReturn(String.valueOf(port));

        List<BookDto> result = bookJpaAdapter.getBooks();

        assertEquals(result.get(0), booksDto.get(0));
        verify(bookRepository).findAll();
    }

    @Test
    void getBookByIdTest(){
        Long id = 1L;
        int port = 8080;
        BookDto bookDto = new BookDto();
        Book book = new Book();

        when(bookRepository.findById(id)).thenReturn(Optional.of(book));
        when(modelMapper.map(any(Book.class), eq(BookDto.class))).thenReturn(bookDto);
        when(env.getProperty("local.server.port")).thenReturn(String.valueOf(port));

        Optional<BookDto> result = bookJpaAdapter.getBookById(id);

        assertEquals(result.get(), bookDto);
        verify(bookRepository).findById(id);
    }
}