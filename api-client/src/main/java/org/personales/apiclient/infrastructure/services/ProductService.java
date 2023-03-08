package org.personales.apiclient.infrastructure.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.personales.apiclient.domain.BookDto;
import org.personales.apiclient.domain.ProductDto;
import org.personales.apiclient.domain.RatingDto;
import org.personales.apiclient.infrastructure.clients.BookApiFeign;
import org.personales.apiclient.infrastructure.clients.RatingAPiFeign;
import org.personales.apiclient.infrastructure.config.CaffeineCacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class ProductService {

    private final BookApiFeign bookApiFeign;
    private final RatingAPiFeign ratingAPiFeign;

    @Cacheable(value = CaffeineCacheConfig.PRODUCTS_CACHE)
    public List<ProductDto> getProducts() {
        log.info("Obteniendo data por el metodo");
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
