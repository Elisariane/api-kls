package com.dev.api_kls.controller;

import com.dev.api_kls.model.Product;
import com.dev.api_kls.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService service;

    @PostMapping
    public ResponseEntity<Product> save(@RequestBody Product product) {
        return new ResponseEntity<>(service.save(product), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return new ResponseEntity<>(service.getAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity getAll(@PathVariable Long id) {
        Optional<Product> product = service.getById(id);

        if (product.isEmpty())
            return new ResponseEntity<>("Não foi possível encontrar esse produto!", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>( product.get(), HttpStatus.OK);
    }


    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @RequestBody Product product) {
        Optional<Product> productByFound = service.getById(id);

        if (productByFound.isEmpty())
            return new ResponseEntity<>("Não foi possível encontrar esse produto!", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(service.update(id, product), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        Optional<Product> productByFound = service.getById(id);

        if (productByFound.isEmpty())
            return new ResponseEntity<>("Não foi possível encontrar esse produto!", HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(service.delete(id), HttpStatus.OK);
    }


}
