package com.example.clientconnectivity.soapconsumer;

import com.example.clientconnectivity.wsdl.SoapOrder;
import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.WebServiceTemplate;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;

import com.example.clientconnectivity.wsdl.SendOrderRequest;
import com.example.clientconnectivity.wsdl.SendOrderResponse;

public class OrderClient extends WebServiceGatewaySupport {

    @Autowired
    Jaxb2Marshaller marshaller;

    private static final Logger log = LoggerFactory.getLogger(OrderClient.class);

//    public SendOrderResponse sendOrder(SoapOrder order) {
//
//        SendOrderRequest request = new SendOrderRequest();
//        request.setOrder(order);
//
//        log.info("Sending order to ovs " + order);
//
//        SendOrderResponse response = (SendOrderResponse) getWebServiceTemplate().setMarshaller(marshaller)
//                .marshalSendAndReceive("http://localhost:5009/ws/orders", request,
//                        new SoapActionCallback(
//                                "http://ordervalidationservice.example.com/soapservice/SendOrderRequest"));
//        return response;
//    }
}