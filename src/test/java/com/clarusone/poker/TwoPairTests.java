package com.clarusone.poker;

import org.junit.jupiter.api.Test;

import static com.clarusone.poker.HandComparisonUtils.assertWin;

/**
 * Tests wherein the best hand is two pairs
 */
public class TwoPairTests {

    @Test
    void two_pairs_beats_a_single_pair() {
        assertWin("2S 2H 4H 5S 4C", "AH AC 5H 6H 7S");
    }

    @Test
    void two_pairs_highest_remaining_card_wins() {
        assertWin("KS KD QD QC 5C", "KS KH QD QS 2C");
    }

    @Test
    void two_pairs_highest_pair_wins() {
        assertWin("QD QS AS AH 2C", "KS KD QD QC 5C");
    }
}
