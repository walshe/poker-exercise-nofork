package com.clarusone.poker;

import org.junit.jupiter.api.Test;

import static com.clarusone.poker.HandComparisonUtils.assertWin;

/**
 * Tests wherein the best hand is a pair
 */
public class PairTests {

    @Test
    void highest_pair_wins() {
        assertWin("AH AC 5H 6H 7S", "6S AD 7H 4S AS");
    }

    @Test
    void pair_beats_a_high_card() {
        assertWin("AH AC 5H 6H 7S", "2S AH 4H 5S KC");
    }
}
