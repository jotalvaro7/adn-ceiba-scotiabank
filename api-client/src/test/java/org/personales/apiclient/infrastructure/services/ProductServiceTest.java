package org.personales.apiclient.infrastructure.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personales.apiclient.domain.BookDto;
import org.personales.apiclient.domain.ProductDto;
import org.personales.apiclient.domain.RatingDto;
import org.personales.apiclient.infrastructure.clients.BookApiFeign;
import org.personales.apiclient.infrastructure.clients.RatingAPiFeign;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private BookApiFeign bookApiFeign;

    @Mock
    private RatingAPiFeign ratingAPiFeign;

    @Mock
    private RedissonClient redissonClient;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(bookApiFeign, ratingAPiFeign, redissonClient);
    }

    @Test
    void getProducts_returnsEmptyList_whenNotBooksOrRatings() throws InterruptedException {

        List<BookDto> books = new ArrayList<>();
        List<RatingDto> ratings = new ArrayList<>();

        RBucket fakeBooksBucket = mock(RBucket.class);
        RBucket fakeRatingsBucket = mock(RBucket.class);

        when(fakeBooksBucket.get()).thenReturn(books);
        when(redissonClient.getBucket("books")).thenReturn(fakeBooksBucket);

        when(fakeRatingsBucket.get()).thenReturn(ratings);
        when(redissonClient.getBucket("ratings")).thenReturn(fakeRatingsBucket);

        List<ProductDto> products = productService.getProducts();

        assertTrue(products.isEmpty());

    }

    @Test
    void getProducts_returnsListOfProducts_whenBooksAndRatingsExist() throws InterruptedException {

        RBucket fakeBooksBucket = mock(RBucket.class);
        RBucket fakeRatingsBucket = mock(RBucket.class);

        when(redissonClient.getBucket("books")).thenReturn(fakeBooksBucket);
        when(fakeBooksBucket.get()).thenReturn(null);

        when(redissonClient.getBucket("ratings")).thenReturn(fakeRatingsBucket);
        when(fakeRatingsBucket.get()).thenReturn(null);

        List<BookDto> bookDtos = Arrays.asList(
                new BookDto(1L, "Book 1", "Algun autor", 5.000, 8090),
                new BookDto(2L, "Book 2",  "Algun autor", 5.000, 8090)
        );
        when(bookApiFeign.getAllBooks()).thenReturn(bookDtos);

        List<RatingDto> ratingDtos = Arrays.asList(
                new RatingDto(1L, 1L, 5, 9080),
                new RatingDto(2L, 2L, 4, 9080)
        );
        when(ratingAPiFeign.getAllRatings()).thenReturn(ratingDtos);

        when(bookApiFeign.getAllBooks()).thenReturn(bookDtos);
        when(ratingAPiFeign.getAllRatings()).thenReturn(ratingDtos);

        List<ProductDto> result = productService.getProducts();

        assertEquals(bookDtos.size(), result.size());
    }

    @Test
    void getProductById_returnsProducts_whenBookAndRatingExist(){
        BookDto bookDto = BookDto.builder().id(1L).build();
        RatingDto ratingDto = RatingDto.builder().bookId(1L).build();

        when(bookApiFeign.getBookById(1L)).thenReturn(bookDto);
        when(ratingAPiFeign.getRatingById(1L)).thenReturn(ratingDto);

        ProductDto product = productService.getProductById(1L, 2);

        assertEquals(bookDto, product.getBookDto());
        assertEquals(ratingDto, product.getRatingDto());
        assertEquals(2, product.getCantidad());
    }
}