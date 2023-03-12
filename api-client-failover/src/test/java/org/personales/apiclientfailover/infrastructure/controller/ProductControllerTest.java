package org.personales.apiclientfailover.infrastructure.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.personales.apiclientfailover.domain.ProductDto;
import org.personales.apiclientfailover.infrastructure.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @Mock
    private ProductService productServicePort;

    private ProductController productController;

    @BeforeEach
    void setUp() {
        productController = new ProductController(productServicePort);
    }

    @Test
    void testAllProducts_whenProductsFound_shouldReturnProducts() throws InterruptedException {

        List<ProductDto> allProducts = Arrays.asList(
                new ProductDto(),
                new ProductDto()
        );

        when(productServicePort.getProducts()).thenReturn(allProducts);

        ResponseEntity<List<ProductDto>> response = productController.allProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(allProducts, response.getBody());

    }

    @Test
    void testAllProducts_whenNotProductsFound_shouldReturnNotFound() throws InterruptedException {

        when(productServicePort.getProducts()).thenReturn(Collections.emptyList());

        ResponseEntity<List<ProductDto>> response = productController.allProducts();

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }

    @Test
    void testGetProductById_whenProductFound_shouldReturnProduct(){

        ProductDto productDto = new ProductDto();

        when(productServicePort.getProductById(1L, 5)).thenReturn(productDto);

        ResponseEntity<ProductDto> response = productController.getProductoById(1L, 5);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productDto, response.getBody());


    }
}