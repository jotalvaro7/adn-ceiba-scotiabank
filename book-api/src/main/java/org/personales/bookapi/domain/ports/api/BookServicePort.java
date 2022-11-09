package org.personales.bookapi.domain.ports.api;

import org.personales.bookapi.domain.data.BookDto;

import java.util.List;
import java.util.Optional;

public interface BookServicePort {

    BookDto addBook(BookDto bookDto);
    void deleteBookById(Long id);
    BookDto updateBook(Long id, BookDto bookDto);
    BookDto getBookById(Long bookId);
    List<BookDto> getBooks();

}
