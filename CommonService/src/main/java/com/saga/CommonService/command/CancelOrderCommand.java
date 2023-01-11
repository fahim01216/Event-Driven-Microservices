package com.saga.CommonService.command;

import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CancelOrderCommand {

    // step : 7
    @TargetAggregateIdentifier
    private String orderId;
    private String orderStatus = "Order Failed";
}
