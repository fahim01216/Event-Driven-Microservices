package com.saga.CommonService.event;

import lombok.Data;

@Data
public class ShipmentCancelledEvent {

    // step : 13
    private String shipmentId;
    private String orderId;
    private String paymentId;
    private String shipmentStatus = "Shipment Cancelled";
}
