package com.example.demo.repository;

import com.example.demo.entity.CreditCard;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CreditCardRepository extends CrudRepository<CreditCard, Long> {
    Optional<CreditCard>findByOib(String oib);

    @Modifying
    void deleteByOib(String oib);
}
