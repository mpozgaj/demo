package com.example.demo.service;

import com.example.demo.entity.CreditCard;
import com.example.demo.openapi.model.NewCardRequest;

public interface CreditCardService {
    CreditCard getCreditCard(String oib);

    void saveCreditCard(NewCardRequest newCardRequest);

    void deleteCreditCar(String oib);
}
