package com.example.clientconnectivity.repository;

import com.example.clientconnectivity.model.Client;
import com.example.clientconnectivity.model.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {

    @Query("SELECT c FROM Client c WHERE c.email = :email")
    public Client findByEmail(@Param("email") String email);
}
