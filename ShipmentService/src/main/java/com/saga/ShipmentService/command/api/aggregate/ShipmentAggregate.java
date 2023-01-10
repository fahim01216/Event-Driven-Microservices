package com.saga.ShipmentService.command.api.aggregate;


import com.saga.CommonService.command.ShipOrderCommand;
import com.saga.CommonService.event.OrderShippedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
public class ShipmentAggregate {

    // step : 13
    @AggregateIdentifier
    private String shipmentId;
    private String orderId;
    private String shipmentStatus;

    public ShipmentAggregate() {
    }

    @CommandHandler
    public ShipmentAggregate(ShipOrderCommand shipOrderCommand) {
        // validate the command

        // publish the Order Shipped Event
        OrderShippedEvent orderShippedEvent = OrderShippedEvent.builder()
                .shipmentId(shipOrderCommand.getShipmentId())
                .orderId(shipOrderCommand.getOrderId())
                .shipmentStatus("Shipment Completed")
                .build();

        AggregateLifecycle.apply(orderShippedEvent);

    }

    // step : 14
    @EventSourcingHandler
    public void on(OrderShippedEvent event) {
        this.shipmentId = event.getShipmentId();
        this.orderId = event.getOrderId();
        this.shipmentStatus = event.getShipmentStatus();
    }
}
