package com.saga.OrderService.command.api.aggregate;


import com.saga.CommonService.command.CompleteOrderCommand;
import com.saga.CommonService.event.OrderCompletedEvent;
import com.saga.OrderService.command.api.command.CreateOrderCommand;
import com.saga.OrderService.command.api.event.OrderCreatedEvent;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class OrderAggregate {

    // step : 3
    // handle the CreateOrderCommand
    @AggregateIdentifier
    private String orderId;
    private String productId;
    private String userId;
    private String addressId;
    private Integer quantity;
    private String orderStatus;

    public OrderAggregate() {
    }

    // register the command and create new event
    @CommandHandler
    public OrderAggregate(CreateOrderCommand createOrderCommand) {

        // validate the command and then create the event


        // step : 4
        // creates new event
        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent();

        // copy the state of all attributes of createOrderCommand
        BeanUtils.copyProperties(createOrderCommand, orderCreatedEvent);

        // sends the data to the axon server
        AggregateLifecycle.apply(orderCreatedEvent);
    }

    // step : 4
    // whatever the data we have added in the event(OrderCreatedEvent), that needs to be updated in aggregate as well
    @EventSourcingHandler
    public void on(OrderCreatedEvent event) {
        this.orderId = event.getOrderId();
        this.userId = event.getUserId();
        this.productId = event.getProductId();
        this.addressId = event.getAddressId();
        this.quantity = event.getQuantity();
        this.orderStatus = event.getOrderStatus();
    }

    // step : 14
    @CommandHandler
    public void handle(CompleteOrderCommand completeOrderCommand) {
        // validate the command

        // publish the Order Completed Event
        OrderCompletedEvent orderCompletedEvent = OrderCompletedEvent.builder()
                .orderId(completeOrderCommand.getOrderId())
                .orderStatus(completeOrderCommand.getOrderStatus())
                .build();

        AggregateLifecycle.apply(orderCompletedEvent);
    }

    // step : 14
    @EventSourcingHandler
    public void on(OrderCompletedEvent event) {
        this.orderStatus = event.getOrderStatus();
    }
}
