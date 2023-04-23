package org.personales.bookapi.domain.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.personales.bookapi.domain.data.BookDto;
import org.personales.bookapi.domain.ports.api.BookServicePort;
import org.personales.bookapi.domain.ports.spi.BookPersistencePort;
import org.personales.bookapi.infrastructure.utils.IOUtilsWrapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class BookServiceImpl implements BookServicePort {

    private final BookPersistencePort bookPersistencePort;

    private final ResourceLoader resourceLoader;
    private final IOUtilsWrapper ioUtilsWrapper;

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

    @Override
    @Cacheable(value = "bookImages", key = "#id")
    public String getImage(Long id) throws IOException {
        Optional<BookDto> book = bookPersistencePort.getBookById(id);
        if (book.isEmpty()) {
            return null;
        }

        Resource resource = resourceLoader.getResource("classpath:static/" + book.get().getImage());
        InputStream inputStream = resource.getInputStream();
        byte[] imagen = ioUtilsWrapper.toByteArray(inputStream);
        log.info("cargando imagen del libro desde la carpeta recursos: {}", id);
        return Base64Utils.encodeToString(imagen);
    }
}
