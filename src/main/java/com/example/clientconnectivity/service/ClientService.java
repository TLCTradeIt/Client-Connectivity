package com.example.clientconnectivity.service;

import com.example.clientconnectivity.model.Client;

public interface ClientService {
    public void saveClient(Client client);

    public boolean isClientAlreadyPresent(Client client);
}
