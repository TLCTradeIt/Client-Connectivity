package com.example.clientconnectivity.controller;

import com.example.clientconnectivity.exception.ResourceNotFoundException;
import com.example.clientconnectivity.model.Portfolio;
import com.example.clientconnectivity.model.Product;
import com.example.clientconnectivity.repository.PortfolioRepository;
import com.example.clientconnectivity.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cc")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

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
    public ResponseEntity<Product> updateProduct(@PathVariable(value = "id") Long productId, @RequestParam(name = "ticker") String ticker,
                                                 @RequestParam(name = "exchange") String exchange,
                                                 @RequestParam(name = "prodQuantity") Integer prodQuantity,
                                                 @RequestParam(name = "portfolioId") Long portfolioId) throws ResourceNotFoundException{

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product does not exist"));

        if(portfolioId != -1){
            Portfolio portfolio = portfolioRepository.findById(portfolioId)
                    .orElseThrow(() -> new ResourceNotFoundException("Portfolio does not exist"));

            product.setPortfolio(portfolio);
        } else{
            product.setPortfolio(null);
        }

        product.setTicker(ticker);
        product.setExchange(exchange);
        product.setProdQuantity(prodQuantity);

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
