package com.example.demo.service;

import com.example.demo.exception.CreditCardNotFoundException;
import com.example.demo.entity.CreditCard;
import com.example.demo.mapper.CreditCardMapper;
import com.example.demo.openapi.model.NewCardRequest;
import com.example.demo.repository.CreditCardRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CreditCardServiceImpl implements CreditCardService {

    private final CreditCardRepository creditCardRepository;
    private final CreditCardMapper mapper = Mappers.getMapper(CreditCardMapper.class);

    @Override
    public CreditCard getCreditCard(final String oib) {
        log.debug("Find credit card with oib: {}", oib);
        final var opt = creditCardRepository.findByOib(oib);
        if (opt.isEmpty()) {
            log.debug("No credit card with oib: {}", oib);
            throw new CreditCardNotFoundException();
        } else
            return opt.get();
    }

    @Override
    public void saveCreditCard(final NewCardRequest newCardRequest) {
        log.debug("Save credit card for OIB: {}", newCardRequest.getOib());
        final var card = mapper.requestToEntity(newCardRequest);
        creditCardRepository.save(card);
        log.info("Credit card for OIB {} saved successfully", newCardRequest.getOib());
    }

    @Transactional
    @Override
    public void deleteCreditCar(final String oib) {
        creditCardRepository.deleteByOib(oib);
        log.info("Credit card with OIB {} deleted successfully", oib);
    }
}
