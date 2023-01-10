package com.saga.OrderService.command.api.event;


import lombok.Data;

@Data
public class OrderCreatedEvent {

    // step : 4
    private String orderId;
    private String productId;
    private String userId;
    private String addressId;
    private Integer quantity;
    private String orderStatus;
}
