package com.usareboot.back.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Objects;

@Getter
@Entity
@Table(name = "cards", schema = "public", catalog = "usareboot")
public class CardsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "card_id")
    private long cardId;
    @Basic
    @Column(name = "card_name")
    private String cardName;
    @Basic
    @Column(name = "percent")
    private double percent;

    public void setCardId(long cardId) {
        this.cardId = cardId;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public void setPercent(double percent) {
        this.percent = percent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardsEntity that = (CardsEntity) o;
        return cardId == that.cardId && Double.compare(percent, that.percent) == 0 && Objects.equals(cardName, that.cardName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardId, cardName, percent);
    }
}
