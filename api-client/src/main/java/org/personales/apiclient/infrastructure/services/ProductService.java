package org.personales.apiclient.infrastructure.services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.personales.apiclient.domain.BookDto;
import org.personales.apiclient.domain.ProductDto;
import org.personales.apiclient.domain.RatingDto;
import org.personales.apiclient.infrastructure.clients.BookApiFeign;
import org.personales.apiclient.infrastructure.clients.RatingAPiFeign;
import org.personales.apiclient.infrastructure.config.CaffeineCacheConfig;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
@Service
public class ProductService {

    private final BookApiFeign bookApiFeign;
    private final RatingAPiFeign ratingAPiFeign;
    private final RedissonClient redissonClient;

    //@Cacheable(value = CaffeineCacheConfig.PRODUCTS_CACHE)
    public List<ProductDto> getProducts() throws InterruptedException {
        RBucket<List<BookDto>> booksBucket = redissonClient.getBucket("books");
        RBucket<List<RatingDto>> ratingsBucket = redissonClient.getBucket("ratings");
        List<BookDto> allBooks = booksBucket.get();
        if(allBooks.isEmpty()){
            Thread.sleep(1000L);
            allBooks = bookApiFeign.getAllBooks();
            booksBucket.set(allBooks, 2, TimeUnit.MINUTES);
        }
        List<RatingDto> allRatings = ratingsBucket.get();
        if(allRatings.isEmpty()){
            allRatings = ratingAPiFeign.getAllRatings();
            ratingsBucket.set(allRatings, 2, TimeUnit.MINUTES);
        }
        List<ProductDto> allProducts;
        if (allBooks.isEmpty() || allRatings.isEmpty()) {
            return Collections.emptyList();
        } else {
            List<RatingDto> finalAllRatings = allRatings;
            allProducts = allBooks.stream()
                    .flatMap(bookDto -> finalAllRatings.stream()
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
