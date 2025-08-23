package com.example.demo.controller;

import com.example.demo.mapper.CreditCardMapper;
import com.example.demo.openapi.api.NewCardRequestApi;
import com.example.demo.openapi.model.CardResponse;
import com.example.demo.openapi.model.NewCardRequest;
import com.example.demo.service.CreditCardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import static com.example.demo.validator.Validator.isValidName;
import static com.example.demo.validator.Validator.isValideOib;

@Slf4j
@RestController
@AllArgsConstructor
public class CreditCardController implements NewCardRequestApi {

    private final CreditCardService creditCardService;
    private final CreditCardMapper mapper = Mappers.getMapper(CreditCardMapper.class);

    @Override
    public ResponseEntity<Void> createCard(final NewCardRequest newCardRequest) {
        log.debug("Create credit card request for OIB: {}", newCardRequest.getOib());
        isValidName(newCardRequest.getFirstName(), "name");
        isValidName(newCardRequest.getLastName(), "last name");
        isValideOib(newCardRequest.getOib());
        log.debug("Credit card create request validated for OIB: {}", newCardRequest.getOib());
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
