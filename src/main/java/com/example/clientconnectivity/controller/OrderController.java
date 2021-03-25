package com.example.clientconnectivity.controller;

import com.example.clientconnectivity.exception.ResourceNotFoundException;
import com.example.clientconnectivity.model.Client;
import com.example.clientconnectivity.model.Order;
import com.example.clientconnectivity.model.Portfolio;
import com.example.clientconnectivity.model.Product;
import com.example.clientconnectivity.repository.ClientRepository;
import com.example.clientconnectivity.repository.OrderRepository;
import com.example.clientconnectivity.repository.PortfolioRepository;
import com.example.clientconnectivity.repository.ProductRepository;
import com.example.clientconnectivity.wsdl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.ws.client.core.WebServiceTemplate;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/cc")
public class OrderController {

    @Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

	@Autowired
	private RestTemplate restTemplate;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private PortfolioRepository portfolioRepository;

    @Autowired
    WebServiceTemplate webServiceTemplate;

    // create an order
    @PostMapping("/create-order")
    public Order createOrder(@RequestParam(name = "productId") Long productId, @RequestParam(name = "clientId") Long clientId,
                             @RequestParam(name = "quantity") Integer quantity, @RequestParam(name = "price") Double price,
                             @RequestParam(name = "side") String side, @RequestParam(name = "status") String status,
                             @RequestParam(name = "portfolioId") Long portfolioId) throws ResourceNotFoundException{

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("The product being referenced does not exist"));

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new ResourceNotFoundException("The client being referenced does not exist"));

        Portfolio portfolio = portfolioRepository.findById(portfolioId)
                .orElseThrow(() -> new ResourceNotFoundException("The portfolio being referenced does not exist"));

        Order order = new Order();
        order.setProduct(product);
        order.setClient(client);
        order.setQuantity(quantity);
        order.setPrice(price);
        order.setSide(side);
        order.setStatus(status);
        order.setPortfolio(portfolio);

        Order createdOrder = this.orderRepository.save(order);


        // creating soap client
        SoapClient soapClient = new SoapClient();
        soapClient.setClientId(client.getClientId());
        soapClient.setAccBalance(client.getAccBalance());

        // creating soap portfolios
        // for order
        SoapPortfolio soapPortfolio = new SoapPortfolio();
        soapPortfolio.setPortfolioId(portfolio.getPortfolioId());

        //for product
        SoapPortfolio productSoapPortfolio = new SoapPortfolio();
        if(product.getPortfolio() != null){
            productSoapPortfolio.setPortfolioId(product.getPortfolio().getPortfolioId());
        }


        // creating soap product
        SoapProduct soapProduct = new SoapProduct();
        soapProduct.setProductId(product.getProductId());
        soapProduct.setTicker(product.getTicker());
        soapProduct.setExchange(product.getExchange());
        soapProduct.setProdQuantity(product.getProdQuantity());
        soapProduct.setPortfolio(productSoapPortfolio);

        // creating soap order with soap client, product and portfolio
        SoapOrder soapOrder = new SoapOrder();
        soapOrder.setOrderId(createdOrder.getOrderId());
        soapOrder.setProduct(soapProduct);
        soapOrder.setClient(soapClient);
        soapOrder.setQuantity(quantity);
        soapOrder.setPrice(price);
        soapOrder.setSide(side);
        soapOrder.setStatus(status);
        soapOrder.setPortfolio(soapPortfolio);
//        soapOrder.setTimestamp(createdOrder.getTimestamp());

        // sending order to ovs
        SendOrderRequest request = new SendOrderRequest();
        request.setOrder(soapOrder);

//        SendOrderResponse response = (SendOrderResponse) webServiceTemplate
//                .marshalSendAndReceive("http://localhost:5009/ws/orders", request);
        SendOrderResponse response = (SendOrderResponse) webServiceTemplate
                .marshalSendAndReceive("https://order-validation.herokuapp.com/ws/orders", request);

        System.out.println("************************** Soap Response ******************************************");
        System.out.println("Order Id: " + response.getOrderId());
        System.out.println("Order validated: " + response.isIsValidated());
        System.out.println("Order status: " + response.getStatus());
        System.out.println("Message: " + response.getMessage());

        // updating order information in the db
        System.out.println("************************** Order Update ******************************************");

//        String url = "http://localhost:5001/api/v1/cc/orders/" + response.getOrderId();
        String url = "https://client-connectivity-trade.herokuapp.com/api/v1/cc/orders/" + response.getOrderId();

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url)
                // Add query parameter
                .queryParam("quantity", order.getQuantity())
                .queryParam("price", order.getPrice())
                .queryParam("side", order.getSide())
                .queryParam("status", response.getStatus());

        restTemplate.put(builder.toUriString(), null);
        System.out.println("Update completed");

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
