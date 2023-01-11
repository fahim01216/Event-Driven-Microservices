package com.saga.CommonService.command;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CancelPaymentCommand {

    // step : 12
    @TargetAggregateIdentifier
    private String paymentId;
    private String orderId;
    private String paymentStatus = "Payment Cancelled";
}
