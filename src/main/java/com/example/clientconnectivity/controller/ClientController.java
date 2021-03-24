package com.example.clientconnectivity.controller;

import com.example.clientconnectivity.exception.ResourceNotFoundException;
import com.example.clientconnectivity.model.Client;
import com.example.clientconnectivity.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cc")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    // create a client
    @PostMapping("/create-client")
    public Client createClient(@RequestBody Client client){
        return this.clientRepository.save(client);
    }

    // get all clients
    @GetMapping("/clients")
    public List<Client> getClient(){
        return this.clientRepository.findAll();
    }

    // get specific client
    @GetMapping("/clients/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable(value = "id") Long clientId) throws ResourceNotFoundException{
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client does not exist"));
        return ResponseEntity.ok().body(client);
    }

    // update an client
    @PutMapping("/clients/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable(value = "id") Long clientId, @Validated @RequestBody Client clientDetails) throws ResourceNotFoundException{
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client does not exist"));

        client.setFirstName(clientDetails.getFirstName());
        client.setLastName(clientDetails.getLastName());
        client.setEmail(clientDetails.getEmail());
        client.setPassword(clientDetails.getPassword());
        client.setPhoneNumber(clientDetails.getPhoneNumber());
        client.setAccBalance(clientDetails.getAccBalance());

        return ResponseEntity.ok(this.clientRepository.save(client));
    }

    // delete a client
    @DeleteMapping("/clients/{id}")
    public Map<String, Boolean> deleteClient(@PathVariable(value = "id") Long clientId) throws ResourceNotFoundException{
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("Client does not exist"));
        this.clientRepository.delete(client);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;

    }


}
