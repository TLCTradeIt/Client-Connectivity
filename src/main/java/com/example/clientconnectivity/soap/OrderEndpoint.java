package com.example.clientconnectivity.soap;

import com.example.clientconnectivity.exception.ResourceNotFoundException;
import com.example.clientconnectivity.model.*;
import com.example.clientconnectivity.repository.ClientRepository;
import com.example.clientconnectivity.repository.OrderRepository;
import com.example.clientconnectivity.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class OrderEndpoint {

    private OrderRepository orderRepository;

    @Autowired
    public OrderEndpoint(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientRepository clientRepository;

    @PayloadRoot(namespace = "http://clientconnectivity.example.com/model", localPart = "GetOrderRequest")
    @ResponsePayload
    public GetOrderResponse processOrderValidationRequest(@RequestPayload GetOrderRequest request) throws ResourceNotFoundException {
        GetOrderResponse response = new GetOrderResponse();

        SoapOrder order = new SoapOrder();

        Order newOrder = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new ResourceNotFoundException("Order does not exist"));

        Product newProduct = productRepository.findById(newOrder.getProduct().getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product does not exist"));

        Client newClient = clientRepository.findById(newOrder.getClient().getClientId())
                .orElseThrow(() -> new ResourceNotFoundException("Client does not exist"));


        SoapProduct product = new SoapProduct();
        product.setProductId(newProduct.getProductId());
        product.setTicker(newProduct.getTicker());
        product.setExchange(newProduct.getExchange());

        SoapClient client = new SoapClient();
        client.setClientId(newClient.getClientId());
        client.setAccBalance(newClient.getAccBalance());

        order.setOrderId(request.getOrderId());
        order.setProduct(product);
        order.setClient(client);
        order.setQuantity(newOrder.getQuantity());
        order.setPrice(newOrder.getPrice());
        order.setSide(newOrder.getSide());
        order.setStatus(newOrder.getStatus());

        response.setOrder(order);

        return response;
    }
}

