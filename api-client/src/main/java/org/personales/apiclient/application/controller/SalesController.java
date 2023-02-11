package org.personales.apiclient.application.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.personales.apiclient.domain.data.BookDto;
import org.personales.apiclient.domain.data.RatingDto;
import org.personales.apiclient.domain.data.SalesDto;
import org.personales.apiclient.infrastructure.feign.bookApi.BookApiFeign;
import org.personales.apiclient.infrastructure.feign.ratingApi.RatingAPiFeign;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Slf4j
@AllArgsConstructor
public class SalesController {


    private final BookApiFeign bookApiFeign;
    private final RatingAPiFeign ratingApiFeign;


    @GetMapping("/listar/books")
    public ResponseEntity<List<SalesDto>> showAllSales() {
        List<BookDto> allBooks = bookApiFeign.getAllBooks();
        List<RatingDto> allRatings = ratingApiFeign.getAllRatings();

        if (allBooks.isEmpty() || allRatings.isEmpty()) {
            log.warn("No se ha encontrado data");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            List<SalesDto> allSales = allBooks.stream()
                    .flatMap(bookDto -> allRatings.stream()
                            .filter(ratingDto -> ratingDto.getBookId().equals(bookDto.getId()))
                            .map(ratingDto -> new SalesDto(bookDto, ratingDto, 1)))
                    .collect(Collectors.toList());
            return new ResponseEntity<>(allSales, HttpStatus.OK);
        }
    }

    @GetMapping("/listar/ratings")
    public ResponseEntity<List<RatingDto>> showAllRatings(){
        List<RatingDto> allRatings = ratingApiFeign.getAllRatings();
        if (allRatings.isEmpty()) {
            log.warn("No se han encontrado ratings");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            log.info("Lista de libros: {}", allRatings);
            return new ResponseEntity<>(allRatings, HttpStatus.OK);
        }
    }

}
