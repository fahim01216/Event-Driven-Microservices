package com.saga.CommonService.command;


import lombok.Value;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Value
public class CancelShipmentCommand {

    // step : 13
    @TargetAggregateIdentifier
    private String shipmentId;
    private String orderId;
    private String paymentId;
    private String shipmentStatus = "Shipment Cancelled";
}
