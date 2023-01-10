package com.saga.CommonService.command;

import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class ShipOrderCommand {

    // step : 12
    @TargetAggregateIdentifier
    private String shipmentId;
    private String orderId;
}
