package org.personales.apiclient.infrastructure.clients;

import org.personales.apiclient.domain.BookDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "msvc-book-api")
public interface BookApiFeign {

    @GetMapping("/api/v1/listar")
    List<BookDto> getAllBooks();

}
