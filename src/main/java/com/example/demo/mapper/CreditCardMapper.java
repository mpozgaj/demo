package com.example.demo.mapper;

import com.example.demo.entity.CreditCard;
import com.example.demo.openapi.model.CardResponse;
import com.example.demo.openapi.model.NewCardRequest;
import org.mapstruct.Mapper;

@Mapper()
public interface CreditCardMapper {

    CardResponse entityToResponse(CreditCard creditCard);

    CreditCard requestToEntity(NewCardRequest request);

}
