package com.saga.CommonService.event;

import lombok.Data;

@Data
public class OrderCancelledEvent {

    // step : 7
    private String orderId;
    private String  orderStatus;
}
