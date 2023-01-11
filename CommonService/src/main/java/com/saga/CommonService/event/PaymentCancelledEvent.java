package com.saga.CommonService.event;

import lombok.Data;

@Data
public class PaymentCancelledEvent {

    // step : 12
    private String paymentId;
    private String orderId;
    private String paymentStatus = "Payment Cancelled";
}
