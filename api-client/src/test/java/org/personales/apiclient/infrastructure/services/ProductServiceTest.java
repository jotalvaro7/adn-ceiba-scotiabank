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
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


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
        BookDto bookDto = BookDto.builder().id(1L).build();
        RatingDto ratingDto = RatingDto.builder().bookId(1L).build();

        RBucket fakeBooksBucket = mock(RBucket.class);
        RBucket fakeRatingsBucket = mock(RBucket.class);

        when(redissonClient.getBucket("books")).thenReturn(fakeBooksBucket);
        when(fakeBooksBucket.get()).thenReturn(Collections.emptyList());

        when(redissonClient.getBucket("ratings")).thenReturn(fakeRatingsBucket);
        when(fakeRatingsBucket.get()).thenReturn(Collections.emptyList());

        when(bookApiFeign.getAllBooks()).thenReturn(Collections.singletonList(bookDto));
        when(ratingAPiFeign.getAllRatings()).thenReturn(Collections.singletonList(ratingDto));

        List<ProductDto> allProducts = productService.getProducts();

        assertEquals(1, allProducts.size());
        assertEquals(bookDto, allProducts.get(0).getBookDto());
        assertEquals(ratingDto, allProducts.get(0).getRatingDto());
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