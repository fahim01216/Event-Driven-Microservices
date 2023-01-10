package com.saga.CommonService.command;

import com.saga.CommonService.model.CardDetail;
import lombok.Builder;
import lombok.Data;
import org.axonframework.modelling.command.TargetAggregateIdentifier;

@Data
@Builder
public class ValidatePaymentCommand {

    // step : 8
    @TargetAggregateIdentifier
    private String paymentId;
    private String orderId;
    private CardDetail cardDetail;
}
