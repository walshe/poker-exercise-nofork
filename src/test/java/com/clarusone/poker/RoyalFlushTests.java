package com.clarusone.poker;

import org.junit.jupiter.api.Test;

import static com.clarusone.poker.HandComparisonUtils.assertTie;
import static com.clarusone.poker.HandComparisonUtils.assertWin;

/**
 * Tests wherein the best hand is a royal flush
 */
public class RoyalFlushTests {

    @Test
    void royal_flush_beats_straight_flush() {
        assertWin("AS QS TS KS JS", "KD QD JD TD 9D");
    }

    @Test
    void royal_flush_tie() {
        assertTie("AS QS TS KS JS", "AH QH TH KH JH");
    }

    @Test
    void royal_flush_beats_four_of_a_kind() {
        assertWin("AS QS TS KS JS", "7H 7C 7D 7S JH");
    }
}
