package com.usareboot.back.repositories;

import com.usareboot.back.entities.CardsEntity;
import com.usareboot.back.entities.ItemsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;

public interface CardsRepository extends JpaRepository<CardsEntity, Long> {

    CardsEntity findCardsEntityByCardName(String cardName);

}

