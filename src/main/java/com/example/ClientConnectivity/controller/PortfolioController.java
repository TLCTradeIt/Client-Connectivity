package com.example.ClientConnectivity.controller;

import com.example.ClientConnectivity.exception.ResourceNotFoundException;
import com.example.ClientConnectivity.model.Portfolio;
import com.example.ClientConnectivity.repository.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cc")
public class PortfolioController {

    @Autowired
    private PortfolioRepository portfolioRepository;

    // create a portfolio
    @PostMapping("/create-portfolio")
    public Portfolio createPortfolio(@RequestBody Portfolio portfolio) {
        return this.portfolioRepository.save(portfolio);
    }

    // get all portfolios
    @GetMapping("/portfolios")
    public List<Portfolio> getPortfolios() {
        return this.portfolioRepository.findAll();
    }

    // get specific portfolio
    @GetMapping("/portfolios/{id}")
    public ResponseEntity<Portfolio> getPortfolioById(@PathVariable(value = "id") Long portfolioId) throws ResourceNotFoundException {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio does not exist"));
        return ResponseEntity.ok().body(portfolio);
    }

    // update a portfolio
    @PutMapping("/portfolios/{id}")
    public ResponseEntity<Portfolio> updatePortfolio(@PathVariable(value = "id") Long portfolioId, @Validated @RequestBody Portfolio portfolioDetails) throws ResourceNotFoundException {
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio does not exist"));

        portfolio.setValue(portfolioDetails.getValue());
        portfolio.setRegisteredProducts(portfolioDetails.getRegisteredProducts());

        return ResponseEntity.ok(this.portfolioRepository.save(portfolio));
    }

    // delete a portfolio
    @DeleteMapping("/portfolios/{id}")
    public Map<String, Boolean> deletePortfolio(@PathVariable(value = "id") Long portfolioId) throws ResourceNotFoundException{
        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("Portfolio does not exist"));
        this.portfolioRepository.delete(portfolio);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }
}
