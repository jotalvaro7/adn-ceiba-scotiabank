package org.personales.bookapi.domain.ports.api;

import org.personales.bookapi.domain.data.BookDto;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface BookServicePort {

    BookDto addBook(BookDto bookDto);
    void deleteBookById(Long id);
    Optional<BookDto> updateBook(Long id, BookDto bookDto);
    Optional<BookDto> getBookById(Long bookId);
    List<BookDto> getBooks();
    String getImage(Long id) throws IOException;

}
