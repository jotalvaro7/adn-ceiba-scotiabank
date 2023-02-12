package org.personales.apiclient.infrastructure.services;

import lombok.AllArgsConstructor;
import org.personales.apiclient.domain.BookDto;
import org.personales.apiclient.domain.ProductDto;
import org.personales.apiclient.domain.RatingDto;
import org.personales.apiclient.infrastructure.clients.BookApiFeign;
import org.personales.apiclient.infrastructure.clients.RatingAPiFeign;
import org.springframework.stereotype.Service;

import java.util.List;

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
            return null;
        } else {
            allProducts = allBooks.stream()
                    .flatMap(bookDto -> allRatings.stream()
                            .filter(ratingDto -> ratingDto.getBookId().equals(bookDto.getId()))
                            .map(ratingDto -> new ProductDto(bookDto, ratingDto, 1)))
                    .toList();
            return allProducts;
        }
    }
}
