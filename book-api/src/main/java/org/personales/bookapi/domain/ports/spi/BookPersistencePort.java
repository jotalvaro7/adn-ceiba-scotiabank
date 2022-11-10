package org.personales.bookapi.domain.ports.spi;

import org.personales.bookapi.domain.data.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookPersistencePort {

    BookDto addBook(BookDto bookDto);
    void deleteBookById(Long id);
    Optional<BookDto> updateBook(Long id,BookDto bookDto);
    List<BookDto> getBooks();
    Optional<BookDto> getBookById(Long bookId);
}

