package com.saga.ShipmentService.command.api.aggregate;


import com.saga.CommonService.command.CancelShipmentCommand;
import com.saga.CommonService.command.ShipOrderCommand;
import com.saga.CommonService.event.OrderShippedEvent;
import com.saga.CommonService.event.ShipmentCancelledEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class ShipmentAggregate {

    // step : 13
    @AggregateIdentifier
    private String shipmentId;
    private String orderId;
    private String paymentId;
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
                .paymentId(shipOrderCommand.getPaymentId())
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

    // step : 13
    @CommandHandler
    public void handle(CancelShipmentCommand cancelShipmentCommand) {
        ShipmentCancelledEvent shipmentCancelledEvent = new ShipmentCancelledEvent();
        BeanUtils.copyProperties(cancelShipmentCommand, shipmentCancelledEvent);
        AggregateLifecycle.apply(shipmentCancelledEvent);
    }

    // step : 13
    @EventSourcingHandler
    public  void on(ShipmentCancelledEvent event) {
        this.shipmentStatus = event.getShipmentStatus();
    }
}
