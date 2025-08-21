package com.example.demo.controller;

import com.example.demo.mapper.CreditCardMapper;
import com.example.demo.openapi.api.NewCardRequestApi;
import com.example.demo.openapi.model.CardResponse;
import com.example.demo.openapi.model.NewCardRequest;
import com.example.demo.service.CreditCardService;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
public class CreditCardController implements NewCardRequestApi {

    private final CreditCardService creditCardService;
    private final CreditCardMapper mapper = Mappers.getMapper(CreditCardMapper.class);

    @Override
    public ResponseEntity<Void> createCard(final NewCardRequest newCardRequest) {
        //request validation
        creditCardService.saveCreditCard(newCardRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<Void> deleteCard(final String oib) {
        creditCardService.deleteCreditCar(oib);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @Override
    public ResponseEntity<CardResponse> getCard(final String oib) {
        final var card = creditCardService.getCreditCard(oib);
        final var response = mapper.entityToResponse(card);
        return ResponseEntity.ok(response);
    }

}
