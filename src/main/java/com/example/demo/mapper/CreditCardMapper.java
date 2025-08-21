package com.example.demo.mapper;

import com.example.demo.entity.CreditCard;
import com.example.demo.openapi.model.CardResponse;
import com.example.demo.openapi.model.NewCardRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface CreditCardMapper {

    CreditCardMapper mapper = Mappers.getMapper(CreditCardMapper.class);

    CardResponse entityToResponse(CreditCard creditCard);

    CreditCard requestToEntity(NewCardRequest request);

}
