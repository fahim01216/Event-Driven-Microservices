package com.saga.ShipmentService.command.api.event;

import com.saga.CommonService.event.OrderShippedEvent;
import com.saga.ShipmentService.command.api.data.Shipment;
import com.saga.ShipmentService.command.api.data.ShipmentRepository;
import org.axonframework.eventhandling.EventHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShipmentEventHandler {

    @Autowired
    private ShipmentRepository shipmentRepository;

    // step : 14
    @EventHandler
    public void on(OrderShippedEvent event) {
        Shipment shipment = new Shipment();
        BeanUtils.copyProperties(event, shipment);
        shipmentRepository.save(shipment);
    }
}
