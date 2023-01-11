package com.saga.PaymentService.command.api.event;


import com.saga.CommonService.event.PaymentCancelledEvent;
import com.saga.CommonService.event.PaymentProcessedEvent;
import com.saga.PaymentService.command.api.data.Payment;
import com.saga.PaymentService.command.api.data.PaymentRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class PaymentEventHandler {

    @Autowired
    private PaymentRepository paymentRepository;

    // step : 11
    @EventHandler
    public void on(PaymentProcessedEvent event) {
        Payment payment = Payment.builder()
                .paymentId(event.getPaymentId())
                .orderId(event.getOrderId())
                .timeStamp(new Date())
                .paymentStatus("Payment Completed")
                .build();

        paymentRepository.save(payment);
    }

    // step : 12
    @EventHandler
    public void on(PaymentCancelledEvent event) {
        Payment payment = paymentRepository.findById(event.getPaymentId()).get();
        payment.setPaymentStatus(event.getPaymentStatus());
        paymentRepository.save(payment);
    }
}
