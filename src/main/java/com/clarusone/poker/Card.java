package com.clarusone.poker;

public final class Card {

    private final Suit suit;
    private final Rank rank;

    /**
     * The individual card suit enum.
     */
    enum Suit {
        CLUBS('C'), DIAMONDS('D'), HEARTS('H'), SPADES('S');

        private final char suitChar;

        /**
         * The individual card suit enum.
         *
         * @param suitChar
         */
        Suit(char suitChar) {
            this.suitChar = suitChar;
        }

        public char getSuitChar() {
            return suitChar;
        }

        public static Suit fromChar(char suitChar) {
            for (Suit suit : Suit.values()) {
                if (suit.getSuitChar() == suitChar) {
                    return suit;
                }
            }
            throw new IllegalArgumentException("No enum constant for character: " + suitChar);
        }
    }

    /**
     * The individual card rank enum.
     */
    enum Rank {
        TWO('2'), THREE('3'), FOUR('4'), FIVE('5'), SIX('6'), SEVEN('7'), EIGHT('8'), NINE('9'), TEN('T'),
        JACK('J'), QUEEN('Q'), KING('K'), ACE('A');

        private final char rankChar;

        Rank(char rankChar) {
            this.rankChar = rankChar;
        }

        public char getRankChar() {
            return rankChar;
        }

        public static Rank fromChar(char rankChar) {
            for (Rank rank : Rank.values()) {
                if (rank.getRankChar() == rankChar) {
                    return rank;
                }
            }
            throw new IllegalArgumentException("No enum constant for character: " + rankChar);
        }

    }

    public Card(final String cardString) {
        if (cardString == null || cardString.length() != 2) {
            throw new IllegalArgumentException("Card must be two characters");
        }
        char[] rankAndSuit = cardString.toCharArray();

        this.rank = Rank.fromChar(rankAndSuit[0]);
        this.suit = Suit.fromChar(rankAndSuit[1]);
    }

    public Suit getSuit() {
        return suit;
    }

    public Rank getRank() {
        return rank;
    }
}
