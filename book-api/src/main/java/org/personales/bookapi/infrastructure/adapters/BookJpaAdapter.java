package org.personales.bookapi.infrastructure.adapters;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.personales.bookapi.domain.data.BookDto;
import org.personales.bookapi.domain.ports.spi.BookPersistencePort;
import org.personales.bookapi.infrastructure.entity.Book;
import org.personales.bookapi.infrastructure.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookJpaAdapter implements BookPersistencePort {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private Environment env;

    @Override
    @Transactional
    public BookDto addBook(BookDto bookDto) {
        Book book = modelMapper.map(bookDto, Book.class);
        Book bookSaved = bookRepository.save(book);
        return modelMapper.map(bookSaved, BookDto.class);
    }

    @Override
    @Transactional
    public void deleteBookById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    @Transactional
    public Optional<BookDto> updateBook(Long id, BookDto bookDto) {
        return bookRepository.findById(id).map(book -> {
            book.setAuthor(bookDto.getAuthor());
            book.setTitle(bookDto.getTitle());
            book.setPrice(bookDto.getPrice());
            return modelMapper.map(bookRepository.save(book), BookDto.class);
        });
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDto> getBooks() {
        return bookRepository.findAll().stream()
                .map(book -> {
                    BookDto bookDto = modelMapper.map(book, BookDto.class);
                    bookDto.setPort(Integer.parseInt(Objects.requireNonNull(env.getProperty("local.server.port"))));
                    return bookDto;
                }).collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<BookDto> getBookById(Long bookId) {
        return bookRepository.findById(bookId).map(book -> {
            BookDto bookDto = modelMapper.map(book, BookDto.class);
            bookDto.setPort(Integer.parseInt(Objects.requireNonNull(env.getProperty("local.server.port"))));
            return bookDto;
        });
    }
}
