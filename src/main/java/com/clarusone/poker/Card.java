package com.clarusone.poker;

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
}
