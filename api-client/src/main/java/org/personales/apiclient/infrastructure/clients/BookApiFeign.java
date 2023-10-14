package org.personales.apiclient.infrastructure.clients;

import org.personales.apiclient.domain.BookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "msvc-book-api")
public interface BookApiFeign {

    @GetMapping("/api/v1/listar")
    List<BookDto> getAllBooks();

    @GetMapping("/api/v1/listar/{bookId}")
    BookDto getBookById(@PathVariable Long bookId);

    @GetMapping("/api/v1/image/{bookId}")
    String getImageById(@PathVariable Long bookId);

}
