package com.clarusone.poker;

import java.util.Objects;

/**
 * Model for a playing card
 */
public final class Card {

    private final CardSuit cardSuit;
    private final CardRank cardRank;

    public Card(final String cardString) {
        if (cardString == null || cardString.length() != 2) {
            throw new IllegalArgumentException("Card must be two characters");
        }
        char[] rankAndSuit = cardString.toCharArray();

        this.cardRank = CardRank.fromChar(rankAndSuit[0]);
        this.cardSuit = CardSuit.fromChar(rankAndSuit[1]);
    }

    public CardSuit getCardSuit() {
        return cardSuit;
    }

    public CardRank getCardRank() {
        return cardRank;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return cardSuit == card.cardSuit && cardRank == card.cardRank;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardSuit, cardRank);
    }
}
