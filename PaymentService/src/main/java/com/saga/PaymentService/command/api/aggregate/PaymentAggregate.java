package com.saga.PaymentService.command.api.aggregate;

import com.saga.CommonService.command.ValidatePaymentCommand;
import com.saga.CommonService.event.PaymentProcessedEvent;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

@Aggregate
@Slf4j
public class PaymentAggregate {

    // step : 9
    @AggregateIdentifier
    private String paymentId;
    private String orderId;
    private String orderStatus;

    public PaymentAggregate() {
    }

    public PaymentAggregate(ValidatePaymentCommand validatePaymentCommand) {
        // validate the payment details

        // publish the payment processed event
        log.info("Executing ValidatePaymentCommand for paymentId: {} " + validatePaymentCommand.getPaymentId() +
                "and for orderId: {} " + validatePaymentCommand.getOrderId());

        // step : 10
        // create PaymentProcessedEvent
        PaymentProcessedEvent paymentProcessedEvent = new PaymentProcessedEvent(validatePaymentCommand.getPaymentId(),
                validatePaymentCommand.getOrderId());

        AggregateLifecycle.apply(paymentProcessedEvent);

        log.info("PaymentProcessedEvent Applied");
    }

    // step : 11
    @EventSourcingHandler
    public void on(PaymentProcessedEvent event) {
        this.paymentId = event.getPaymentId();
        this.orderId = event.getOrderId();
    }
}
