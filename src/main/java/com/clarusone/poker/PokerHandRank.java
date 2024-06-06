package com.clarusone.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * The PokerHand rank enum.
 * Ordinal of the enum represents the ranking of the hand from 0 (lowest) to 9 (highest)
 */
enum PokerHandRank {
    HIGH_CARD,
    PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    STRAIGHT,
    FLUSH,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    STRAIGHT_FLUSH,
    ROYAL_FLUSH;

    /**
     * Calculate the rank of the hand.
     * Starting from highest type of hand, try to check if current hand matches
     * if not see if it fits next highest hand
     * do until we get to lowest ranking hand.
     * PokerHandRank ordinal will represents the poker hand ranking low to high
     *
     * @param cards
     * @return PokerHandRank
     */
    public static PokerHandRank fromCards(List<Card> cards) {
        if (cards == null || cards.size() != 5) {
            throw new IllegalArgumentException("Exactly 5 cards are required.");
        }

        boolean isFlush = Utils.isFlush(cards);
        boolean isStraight = Utils.isStraight(cards);

        if (isFlush && isStraight && cards.get(4).getCardRank() == CardRank.ACE) {
            return ROYAL_FLUSH;
        }

        if (isFlush && isStraight) {
            return STRAIGHT_FLUSH;
        }

        if (isFlush) {
            return FLUSH;
        }

        if (isStraight) {
            return STRAIGHT;
        }

        Map<CardRank, Long> rankFrequency = Utils.getCardRankFrequency(cards);

        if (rankFrequency.containsValue(4L)) {
            return FOUR_OF_A_KIND;
        }

        if (rankFrequency.containsValue(3L) && rankFrequency.containsValue(2L)) {
            return FULL_HOUSE;
        }

        if (rankFrequency.containsValue(3L)) {
            return THREE_OF_A_KIND;
        }

        if (Collections.frequency(new ArrayList<>(rankFrequency.values()), 2L) == 2) {
            return TWO_PAIR;
        }

        if (rankFrequency.containsValue(2L)) {
            return PAIR;
        }

        return HIGH_CARD;
    }

}
