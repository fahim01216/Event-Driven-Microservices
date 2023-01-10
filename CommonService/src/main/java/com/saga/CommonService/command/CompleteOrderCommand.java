package com.saga.CommonService.command;


import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class CompleteOrderCommand {

    // step : 13
    @TargetAggregateIdentifier
    private String orderId;
    private String orderStatus;
}
