package org.personales.apiclient.infrastructure.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.personales.apiclient.domain.ProductDto;
import org.personales.apiclient.infrastructure.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class ProductController {


    private final ProductService productServicePort;


    @GetMapping("/listar/")
    public ResponseEntity<List<ProductDto>> allProducts() {
        List<ProductDto> allProducts = productServicePort.getProducts();
        if (allProducts.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(allProducts, HttpStatus.OK);
        }

    }
}
