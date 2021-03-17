package com.example.ClientConnectivity.controller;

import com.example.ClientConnectivity.exception.ResourceNotFoundException;
import com.example.ClientConnectivity.model.Order;
import com.example.ClientConnectivity.repository.OrderRepository;
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
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    // create an order
    @PostMapping("/create-order")
    public Order createOrder(@RequestBody Order order){
        return this.orderRepository.save(order);
    }

    // get all orders
    @GetMapping("/orders")
    public List<Order> getOrders(){
        return this.orderRepository.findAll();
    }

    // get specific order
    @GetMapping("/orders/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable(value = "id") Long orderId) throws ResourceNotFoundException{
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order does not exist"));
        return ResponseEntity.ok().body(order);
    }

    // update an order
    @PutMapping("/orders/{id}")
    public ResponseEntity<Order> updateOrder(@PathVariable(value = "id") Long orderId, @Validated @RequestBody Order orderDetails) throws ResourceNotFoundException{
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order does not exist"));

        order.setProduct(orderDetails.getProduct());
        order.setQuantity(orderDetails.getQuantity());
        order.setPrice(orderDetails.getPrice());
        order.setSide(orderDetails.getSide());
        order.setStatus(orderDetails.getStatus());

        return ResponseEntity.ok(this.orderRepository.save(order));
    }

    // delete an order
    @DeleteMapping("/orders/{id}")
    public Map<String, Boolean> deleteOrder(@PathVariable(value = "id") Long orderId) throws ResourceNotFoundException{
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order does not exist"));
        this.orderRepository.delete(order);

        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);

        return response;
    }

    

}
