package com.example.ClientConnectivity.controller;

import com.example.ClientConnectivity.exception.ResourceNotFoundException;
import com.example.ClientConnectivity.model.Client;
import com.example.ClientConnectivity.model.Order;
import com.example.ClientConnectivity.model.Product;
import com.example.ClientConnectivity.repository.ClientRepository;
import com.example.ClientConnectivity.repository.OrderRepository;
import com.example.ClientConnectivity.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cc")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientRepository clientRepository;

    // create an order
    @PostMapping("/create-order")
    public Order createOrder(@RequestParam(name = "productId") Long productId, @RequestParam(name = "clientId") Long clientId,
                             @RequestParam(name = "quantity") Integer quantity, @RequestParam(name = "price") Double price,
                             @RequestParam(name = "side") String side, @RequestParam(name = "status") String status) throws ResourceNotFoundException{

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("The product being referenced does not exist"));

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("The client being referenced does not exist"));

        Order order = new Order();
        order.setProduct(product);
        order.setClient(client);
        order.setQuantity(quantity);
        order.setPrice(price);
        order.setSide(side);
        order.setStatus(status);

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
    public ResponseEntity<Order> updateOrder(@PathVariable(value = "id") Long orderId, @RequestParam(name = "quantity") Integer quantity,
                                             @RequestParam(name = "price") Double price,
                                             @RequestParam(name = "side") String side,
                                             @RequestParam(name = "status") String status) throws ResourceNotFoundException{

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order does not exist"));

        order.setQuantity(quantity);
        order.setPrice(price);
        order.setSide(side);
        order.setStatus(status);

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
