package com.clarusone.poker;

import java.util.*;
import java.util.stream.Collectors;

public final class Utils {

    /**
     * Check if the cards are a flush - all same Card Suit
     * @param cards
     * @return
     */
    static boolean isFlush(final List<Card> cards) {
        CardSuit firstSuit = cards.get(0).getCardSuit();
        return cards.stream().allMatch(card -> card.getCardSuit() == firstSuit);
    }

    /**
     * Check if the cards are a straight.
     * Assumes that cards are ranked ascending by card rank.
     * @param cards
     * @return boolean
     */
    static boolean isStraight(final List<Card> cards) {
        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i).getCardRank().ordinal() + 1 != cards.get(i + 1).getCardRank().ordinal()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Calculate the card rank frequency of the cards.
     * returns a map where Key represents a Card Rank and the value represents the number of times that Card Rank appears in the hand
     * @param cards
     * @return Map<Card.Rank, Long>
     */
    static Map<CardRank, Long> getCardRankFrequency(final List<Card> cards) {
        return cards.stream()
                .collect(Collectors.groupingBy(Card::getCardRank, Collectors.counting()));
    }
}
