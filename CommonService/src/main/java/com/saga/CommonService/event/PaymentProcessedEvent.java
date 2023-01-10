package com.saga.CommonService.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentProcessedEvent {

    // step : 10
    private String paymentId;
    private String orderId;
}
