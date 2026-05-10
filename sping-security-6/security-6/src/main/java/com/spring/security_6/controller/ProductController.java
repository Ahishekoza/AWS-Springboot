package com.spring.security_6.controller;


import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ProductController {

    public record Product(Long productId,String name, double price) {};

    List<Product> products = new ArrayList<>(
            List.of(
                    new Product(1L,"Laptop", 999.99),
                    new Product(2L,"Smartphone", 499.99),
                    new Product(3L,"Headphones", 199.99)
            )
    );


    @GetMapping("/products")
    public List<Product> getAllProducts(){
        return products;
    }

    @PostMapping("/product")
    public Product createNewProduct(@RequestBody Product product){
        products.add(product);
        return product;
    }
}
