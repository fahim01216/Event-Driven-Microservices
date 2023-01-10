package com.saga.UserService.projection;

import com.saga.CommonService.model.CardDetail;
import com.saga.CommonService.model.User;
import com.saga.CommonService.query.GetUserPaymentDetailQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

@Component
public class UserProjection {

    // step : 7
    @QueryHandler
    public User getUserPaymentDetails(GetUserPaymentDetailQuery query) {
        // ideally get the data from the database

        CardDetail cardDetail = CardDetail.builder()
                .name("Md Fahim")
                .cardNumber("2524128729281201")
                .expirationMonth(12)
                .getExpirationYear(2026)
                .cvvNumber(201)
                .build();

        return User.builder()
                .userId(query.getUserId())
                .firstName("Md")
                .lastName("Fahim")
                .cardDetail(cardDetail)
                .build();
    }
}
