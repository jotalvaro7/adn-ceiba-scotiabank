package org.personales.apiclient.application.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.personales.apiclient.domain.data.BookDto;
import org.personales.apiclient.domain.data.RatingDto;
import org.personales.apiclient.domain.data.ProductDto;
import org.personales.apiclient.infrastructure.feign.bookApi.BookApiFeign;
import org.personales.apiclient.infrastructure.feign.ratingApi.RatingAPiFeign;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class ProductController {


    private final BookApiFeign bookApiFeign;
    private final RatingAPiFeign ratingApiFeign;


    @GetMapping("/listar/productos")
    public ResponseEntity<List<ProductDto>> allProducts() {
        List<BookDto> allBooks = bookApiFeign.getAllBooks();
        List<RatingDto> allRatings = ratingApiFeign.getAllRatings();

        if (allBooks.isEmpty() || allRatings.isEmpty()) {
            log.warn("No se ha encontrado data");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<ProductDto> allProducts = allBooks.stream()
                    .flatMap(bookDto -> allRatings.stream()
                            .filter(ratingDto -> ratingDto.getBookId().equals(bookDto.getId()))
                            .map(ratingDto -> new ProductDto(bookDto, ratingDto, 1)))
                    .toList();
            log.info("Lista de productos: {}", allProducts);
            return new ResponseEntity<>(allProducts, HttpStatus.OK);
        }
    }
}
