package org.personales.apiclientfailover.infrastructure.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personales.apiclientfailover.domain.BookDto;
import org.personales.apiclientfailover.domain.ProductDto;
import org.personales.apiclientfailover.domain.RatingDto;
import org.personales.apiclientfailover.infrastructure.clients.BookApiFeign;
import org.personales.apiclientfailover.infrastructure.clients.RatingAPiFeign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

    @Mock
    private BookApiFeign bookApiFeign;

    @Mock
    private RatingAPiFeign ratingAPiFeign;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        productService = new ProductService(bookApiFeign, ratingAPiFeign);
    }

    @Test
    void getAllProducts_returnsEmptyList_whenNotBooksOrRatings() {
        List<BookDto> books = new ArrayList<>();
        List<RatingDto> ratings = new ArrayList<>();

        when(bookApiFeign.getAllBooks()).thenReturn(books);
        when(ratingAPiFeign.getAllRatings()).thenReturn(ratings);

        List<ProductDto> products = productService.getProducts();

        assertTrue(products.isEmpty());

    }

    @Test
    void getAllProducts_returnsListsOfProducts_whenBooksAndRatingsExist() {
        BookDto bookDto = BookDto.builder().id(1L).build();
        RatingDto ratingDto = RatingDto.builder().bookId(1L).build();

        when(bookApiFeign.getAllBooks()).thenReturn(Collections.singletonList(bookDto));
        when(ratingAPiFeign.getAllRatings()).thenReturn(Collections.singletonList(ratingDto));

        List<ProductDto> allProducts = productService.getProducts();

        assertEquals(1, allProducts.size());
        assertEquals(bookDto, allProducts.get(0).getBookDto());
        assertEquals(ratingDto, allProducts.get(0).getRatingDto());
    }

    @Test
    void getProcuctById_returnProduct_whenBookAndRatingExist() {
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