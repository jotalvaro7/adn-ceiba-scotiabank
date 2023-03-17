package org.personales.bookapi.domain.service;

import lombok.AllArgsConstructor;
import org.personales.bookapi.domain.data.BookDto;
import org.personales.bookapi.domain.ports.api.BookServicePort;
import org.personales.bookapi.domain.ports.spi.BookPersistencePort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookServicePort {

    private final BookPersistencePort bookPersistencePort;

    @Override
    public BookDto addBook(BookDto bookDto) {
        return bookPersistencePort.addBook(bookDto);
    }

    @Override
    public void deleteBookById(Long id) {
        bookPersistencePort.deleteBookById(id);
    }

    @Override
    public Optional<BookDto> updateBook(Long id, BookDto bookDto) {
        return bookPersistencePort.updateBook(id, bookDto);
    }

    @Override
    public Optional<BookDto> getBookById(Long bookId) {
        return bookPersistencePort.getBookById(bookId);
    }

    @Override
    public List<BookDto> getBooks() {
        return bookPersistencePort.getBooks();
    }
}
