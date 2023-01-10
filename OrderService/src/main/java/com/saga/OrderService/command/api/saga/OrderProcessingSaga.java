package com.saga.OrderService.command.api.saga;


import com.saga.CommonService.command.CompleteOrderCommand;
import com.saga.CommonService.command.ShipOrderCommand;
import com.saga.CommonService.command.ValidatePaymentCommand;
import com.saga.CommonService.event.OrderCompletedEvent;
import com.saga.CommonService.event.OrderShippedEvent;
import com.saga.CommonService.event.PaymentProcessedEvent;
import com.saga.CommonService.model.User;
import com.saga.CommonService.query.GetUserPaymentDetailQuery;
import com.saga.OrderService.command.api.event.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.modelling.saga.EndSaga;
import org.axonframework.modelling.saga.SagaEventHandler;
import org.axonframework.modelling.saga.StartSaga;
import org.axonframework.queryhandling.QueryGateway;
import org.axonframework.spring.stereotype.Saga;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Saga
@Slf4j
public class OrderProcessingSaga {

    @Autowired
    private CommandGateway commandGateway;

    @Autowired
    private QueryGateway queryGateway;

    // step: 5
    @StartSaga
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderCreatedEvent event) {
        log.info("OrderCreatedEvent in Saga for OrderId : {}", event.getOrderId());

        // step : 7
        GetUserPaymentDetailQuery getUserPaymentDetailQuery = new GetUserPaymentDetailQuery(event.getUserId());

        User user = null;

        try {
            user = queryGateway.query(getUserPaymentDetailQuery, ResponseTypes.instanceOf(User.class)).join();
        } catch(Exception e) {
            log.error(e.getMessage());

            // start the compensating transaction
        }


        // Step : 8
        // create ValidatePaymentCommand
        ValidatePaymentCommand validatePaymentCommand = ValidatePaymentCommand.builder()
                .paymentId(UUID.randomUUID().toString())
                .orderId(event.getOrderId())
                .cardDetail(user.getCardDetail())
                .build();

        commandGateway.sendAndWait(validatePaymentCommand);
    }

    @SagaEventHandler(associationProperty = "orderId")
    private void handle(PaymentProcessedEvent event) {

        // step : 12
        log.info("PaymentProcessedEvent in Saga for OrderId : {}", event.getOrderId());

        try {
            ShipOrderCommand shipOrderCommand = ShipOrderCommand.builder()
                    .shipmentId(UUID.randomUUID().toString())
                    .orderId(event.getOrderId())
                    .build();

            commandGateway.send(shipOrderCommand);
        } catch (Exception e) {
            log.error(e.getMessage());

            // start the compensating transaction
        }
    }

    // step : 13
    @SagaEventHandler(associationProperty = "orderId")
    public void handle(OrderShippedEvent event) {

        log.info("OrderShippedEvent in Saga for OrderId : {}", event.getOrderId());

        CompleteOrderCommand completeOrderCommand = CompleteOrderCommand.builder()
                .orderId(event.getOrderId())
                .orderStatus("Order Approved")
                .build();

        commandGateway.send(completeOrderCommand);
    }

    // step : 15
    @SagaEventHandler(associationProperty = "orderId")
    @EndSaga
    public void handle(OrderCompletedEvent event) {
        log.info("OrderShippedEvent in Saga for OrderId : {}", event.getOrderId());

    }
}
