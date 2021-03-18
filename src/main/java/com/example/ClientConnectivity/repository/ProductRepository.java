package com.example.ProductConnectivity.repository;

import com.example.ClientConnectivity.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Order, Long> {
}
