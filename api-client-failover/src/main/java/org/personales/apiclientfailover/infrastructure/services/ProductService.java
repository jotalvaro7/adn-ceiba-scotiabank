package org.personales.apiclientfailover.infrastructure.services;

import lombok.AllArgsConstructor;
import org.personales.apiclientfailover.domain.BookDto;
import org.personales.apiclientfailover.domain.ProductDto;
import org.personales.apiclientfailover.domain.RatingDto;
import org.personales.apiclientfailover.infrastructure.clients.BookApiFeign;
import org.personales.apiclientfailover.infrastructure.clients.RatingAPiFeign;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ProductService {

    private final BookApiFeign bookApiFeign;
    private final RatingAPiFeign ratingAPiFeign;

    public List<ProductDto> getProducts() {
        List<BookDto> allBooks = bookApiFeign.getAllBooks();
        List<RatingDto> allRatings = ratingAPiFeign.getAllRatings();
        List<ProductDto> allProducts;
        if (allBooks.isEmpty() || allRatings.isEmpty()) {
            return Collections.emptyList();
        } else {
            allProducts = allBooks.stream()
                    .flatMap(bookDto -> allRatings.stream()
                            .filter(ratingDto -> ratingDto.getBookId().equals(bookDto.getId()))
                            .map(ratingDto -> new ProductDto(bookDto, ratingDto, 1)))
                    .collect(Collectors.toList());
            return allProducts;
        }
    }

    public ProductDto getProductById(Long id, Integer cantidad){
        BookDto bookDto = bookApiFeign.getBookById(id);
        RatingDto ratingDto = ratingAPiFeign.getRatingById(id);

        return ProductDto.builder()
                .bookDto(bookDto)
                .ratingDto(ratingDto)
                .cantidad(cantidad)
                .build();
    }
}
