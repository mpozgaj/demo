package com.example.demo.service;

import com.example.demo.exception.CreditCardNotFoundException;
import com.example.demo.entity.CreditCard;
import com.example.demo.mapper.CreditCardMapper;
import com.example.demo.openapi.model.NewCardRequest;
import com.example.demo.repository.CreditCardRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final CreditCardMapper mapper = Mappers.getMapper(CreditCardMapper.class);

    @Override
    public CreditCard getCreditCard(String oib) {
        final var opt = creditCardRepository.findByOib(oib);
        if (opt.isEmpty()) {
            throw new CreditCardNotFoundException();
        } else
            return opt.get();
    }

    @Override
    public void saveCreditCard(NewCardRequest newCardRequest) {
        final var card = mapper.requestToEntity(newCardRequest);
        creditCardRepository.save(card);

    }

    @Transactional
    @Override
    public void deleteCreditCar(String oib) {
        creditCardRepository.deleteByOib(oib);
    }
}
