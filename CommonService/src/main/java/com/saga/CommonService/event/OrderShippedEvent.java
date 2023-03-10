package com.saga.CommonService.event;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderShippedEvent {

    // step : 13
    private String shipmentId;
    private String orderId;
    private String paymentId;
    private String shipmentStatus;
}
