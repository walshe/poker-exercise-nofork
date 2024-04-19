package com.clarusone.poker;

import org.junit.jupiter.api.Test;

import static com.clarusone.poker.HandComparisonUtils.assertWin;

/**
 * Tests wherein the best hand is a straight flush
 */
public class StraightFlushTests {

    @Test
    void highest_straight_flush_wins() {
        assertWin("KS AS TS QS JS", "2H 3H 4H 5H 6H");
    }

    @Test
    void straight_flush_beats_4_of_a_kind() {
        assertWin("2H 3H 4H 5H 6H", "AS AD AC AH JD");
    }
}
