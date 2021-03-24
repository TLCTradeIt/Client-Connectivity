package com.example.clientconnectivity.repository;

import com.example.clientconnectivity.model.Client;
import com.example.clientconnectivity.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

//    public List<Portfolio>
}