package com.example.ClientConnectivity.controller;

import com.example.ClientConnectivity.exception.ResourceNotFoundException;
import com.example.ClientConnectivity.model.Product;
import com.example.ClientConnectivity.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cc")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    // create a product
    @PostMapping("/create-product")
    public Product createProduct(@RequestBody Product product){
        return this.productRepository.save(product);
    }

    // get all products
    @GetMapping("/products")
    public List<Product> getProduct(){
        return this.productRepository.findAll();
    }

    // get specific product
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable(value = "id") Long productId) throws ResourceNotFoundException{
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product does not exist"));
        return ResponseEntity.ok().body(product);
    }

    // update an product
    @PutMapping("/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") Long productId, @Validated @RequestBody Product productDetails) throws ResourceNotFoundException{
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product does not exist"));

        product.setTicker(productDetails.getTicker());
        product.setExchange(productDetails.getExchange());

        return ResponseEntity.ok(this.productRepository.save(product));
    }

    // delete a product
    @DeleteMapping("/products/{id}")
    public Map<String, Boolean> deleteProduct(@PathVariable(value = "id") Long productId) throws ResourceNotFoundException{
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product does not exist"));
        this.productRepository.delete(product);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }

    

}
