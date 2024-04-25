package com.clarusone.poker;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HandComparisonUtils {

    private static int compareHands(String hand1, String hand2) {
        return new PokerHand(hand1).compareTo(new PokerHand(hand2));
    }

    public static void assertWin(String winner, String loser) {
        int difference = compareHands(winner, loser);
        assertTrue(difference > 0);
    }

    public static void assertTie(String hand1, String hand2) {
        int difference = compareHands(hand1, hand2);
        assertEquals(0, difference);
    }
}
