package com.example.clientconnectivity.service;

import com.example.clientconnectivity.model.Client;
import com.example.clientconnectivity.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService{

    @Autowired
    BCryptPasswordEncoder encoder;

    @Autowired
    ClientRepository clientRepository;


    @Override
    public void saveClient(Client client) {
        client.setPassword(encoder.encode(client.getPassword()));
//        client.setStatus("VERIFIED");
//        Role userRole = roleRepository.findByRole("SITE_USER");
//        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        client.setRole("SITE_USER");
        clientRepository.save(client);
    }

    @Override
    public boolean isClientAlreadyPresent(Client client) {
        boolean isUserAlreadyExists = false;
        Client existingClient = clientRepository.findByEmail(client.getEmail());
        // If user is found in database, then then user already exists.
        if(existingClient != null){
            isUserAlreadyExists = true;
        }
        return isUserAlreadyExists;
    }
}
