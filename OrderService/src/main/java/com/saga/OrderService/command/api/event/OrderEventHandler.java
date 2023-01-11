package com.saga.OrderService.command.api.event;


import com.saga.CommonService.event.OrderCancelledEvent;
import com.saga.CommonService.event.OrderCompletedEvent;
import com.saga.OrderService.command.api.data.Order;
import com.saga.OrderService.command.api.data.OrderRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class OrderEventHandler {

    @Autowired
    private OrderRepository orderRepository;

    // step : 5
    @EventHandler
    public void on(OrderCreatedEvent event) {
        Order order = new Order();

        // copy the state of all attributes of OrderCreatedEvent to Order object
        BeanUtils.copyProperties(event, order);

        // save the data in database
        orderRepository.save(order);
    }

    // step : 15
    @EventHandler
    public void on(OrderCompletedEvent event) {
        Order order = orderRepository.findById(event.getOrderId()).get();
        order.setOrderStatus(event.getOrderStatus());
        orderRepository.save(order);
    }

    // step : 7
    @EventHandler
    public void on(OrderCancelledEvent event) {
        Order order = orderRepository.findById(event.getOrderId()).get();
        order.setOrderStatus(event.getOrderStatus());
        orderRepository.save(order);
    }
}
