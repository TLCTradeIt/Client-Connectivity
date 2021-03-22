package com.example.clientconnectivity.controller;

import com.example.clientconnectivity.exception.ResourceNotFoundException;
import com.example.clientconnectivity.model.Client;
import com.example.clientconnectivity.model.Order;
import com.example.clientconnectivity.model.Product;
import com.example.clientconnectivity.repository.ClientRepository;
import com.example.clientconnectivity.repository.OrderRepository;
import com.example.clientconnectivity.repository.ProductRepository;
import com.example.clientconnectivity.wsdl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.ws.client.core.WebServiceTemplate;

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

    @Autowired
    WebServiceTemplate webServiceTemplate;

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

        Order createdOrder = this.orderRepository.save(order);

        // creating soap product
        SoapProduct soapProduct = new SoapProduct();
        soapProduct.setProductId(product.getProductId());
        soapProduct.setTicker(product.getTicker());
        soapProduct.setExchange(product.getExchange());

        // creating soap client
        SoapClient soapClient = new SoapClient();
        soapClient.setClientId(client.getClientId());
        soapClient.setAccBalance(client.getAccBalance());

        // creating soap order with soap client and product
        SoapOrder soapOrder = new SoapOrder();
        soapOrder.setOrderId(createdOrder.getOrderId());
        soapOrder.setProduct(soapProduct);
        soapOrder.setClient(soapClient);
        soapOrder.setQuantity(quantity);
        soapOrder.setPrice(price);
        soapOrder.setSide(side);
        soapOrder.setStatus(status);
//        soapOrder.setTimestamp(createdOrder.getTimestamp());

        // sending order to ovs
        SendOrderRequest request = new SendOrderRequest();
        request.setOrder(soapOrder);

        SendOrderResponse response = (SendOrderResponse) webServiceTemplate
                .marshalSendAndReceive("http://localhost:5009/ws/orders", request);

        System.out.println("************************** Soap Response ******************************************");
        System.out.println("Order Id: " + response.getOrderId());
        System.out.println("Order validated: " + response.isIsValidated());
        System.out.println("Order status: " + response.getStatus());
        System.out.println("Message: " + response.getMessage());

        return createdOrder;
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
