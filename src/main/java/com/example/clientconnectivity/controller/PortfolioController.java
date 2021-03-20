package com.example.clientconnectivity.controller;

import com.example.clientconnectivity.exception.ResourceNotFoundException;
import com.example.clientconnectivity.model.Client;
import com.example.clientconnectivity.model.Portfolio;
import com.example.clientconnectivity.repository.ClientRepository;
import com.example.clientconnectivity.repository.PortfolioRepository;
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

    @Autowired
    private ClientRepository clientRepository;

    // create a portfolio
    @PostMapping("/create-portfolio")
    public Portfolio createPortfolio(@RequestParam(name = "clientId") Long clientId, @RequestParam(name = "name") String name, @RequestParam(name = "value") Double value) throws ResourceNotFoundException{

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("The client being referenced does not exist"));

        Portfolio portfolio = new Portfolio();
        portfolio.setName(name);
        portfolio.setValue(value);
        portfolio.setClient(client);

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
