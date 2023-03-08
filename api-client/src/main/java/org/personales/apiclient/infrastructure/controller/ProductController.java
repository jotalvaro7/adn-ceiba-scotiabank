package org.personales.apiclient.infrastructure.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.personales.apiclient.domain.ProductDto;
import org.personales.apiclient.infrastructure.services.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class ProductController {

    private final ProductService productServicePort;

    @GetMapping("/listar")
    public ResponseEntity<List<ProductDto>> allProducts() throws InterruptedException {
        log.info("Consultando productos");
        List<ProductDto> allProducts = productServicePort.getProducts();
        if (allProducts.isEmpty()) {
            log.warn("Productos no encontrados");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            log.info("Productos encontrados: {}", allProducts);
            return new ResponseEntity<>(allProducts, HttpStatus.OK);
        }
    }

    @GetMapping("/listar/{productoId}/cantidad/{cantidad}")
    public ResponseEntity<ProductDto> getProductoById(@PathVariable Long productoId, @PathVariable Integer cantidad){
        log.info("Consultando producto con Id: {}", productoId);

        if(productoId.equals(2L)){
            throw new IllegalStateException("Producto no encontrado");
        }

        ProductDto productById = productServicePort.getProductById(productoId, cantidad);
        log.info("Producto con id: {} encontrado", productoId);
        return new ResponseEntity<>(productById, HttpStatus.OK);
    }
}
