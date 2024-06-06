package com.clarusone.poker;

/**
 * The individual card suit enum.
 */
enum CardSuit {
    CLUBS('C'), DIAMONDS('D'), HEARTS('H'), SPADES('S');

    private final char cardSuitChar;

    /**
     * The individual card suit enum.
     *
     * @param cardSuitChar
     */
    CardSuit(char cardSuitChar) {
        this.cardSuitChar = cardSuitChar;
    }

    public char getCardSuitChar() {
        return cardSuitChar;
    }

    public static CardSuit fromChar(char cardSuitChar) {
        for (CardSuit cardSuit : CardSuit.values()) {
            if (cardSuit.getCardSuitChar() == cardSuitChar) {
                return cardSuit;
            }
        }
        throw new IllegalArgumentException("No enum constant for character: " + cardSuitChar);
    }
}
