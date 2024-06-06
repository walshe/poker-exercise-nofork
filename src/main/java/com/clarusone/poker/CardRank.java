package com.clarusone.poker;

/**
 * The individual card rank enum.
 */
enum CardRank {
    TWO('2'), THREE('3'), FOUR('4'), FIVE('5'), SIX('6'), SEVEN('7'), EIGHT('8'), NINE('9'), TEN('T'),
    JACK('J'), QUEEN('Q'), KING('K'), ACE('A');

    private final char cardRankChar;

    CardRank(char cardRankChar) {
        this.cardRankChar = cardRankChar;
    }

    public char getCardRankChar() {
        return cardRankChar;
    }

    public static CardRank fromChar(char cardRankChar) {
        for (CardRank cardRank : CardRank.values()) {
            if (cardRank.getCardRankChar() == cardRankChar) {
                return cardRank;
            }
        }
        throw new IllegalArgumentException("No enum constant for character: " + cardRankChar);
    }

}
