package com.saga.OrderService.command.api.controller;


import com.saga.OrderService.command.api.command.CreateOrderCommand;
import com.saga.OrderService.command.api.model.OrderRestModel;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderCommandController {

    @Autowired
    private transient CommandGateway commandGateway;

    // step : 1
    // handle post request for creating the order
    @PostMapping
    public String createOrder(@RequestBody OrderRestModel orderRestModel) {

        // create & publish this command to the commandGateway
        CreateOrderCommand createOrderCommand = CreateOrderCommand.builder()
                .orderId(UUID.randomUUID().toString())
                .userId(orderRestModel.getUserId())
                .productId(orderRestModel.getProductId())
                .addressId(orderRestModel.getAddressId())
                .quantity(orderRestModel.getQuantity())
                .orderStatus("Order Created")
                .build();

        commandGateway.sendAndWait(createOrderCommand);

        return "Order Created";
    }
}
