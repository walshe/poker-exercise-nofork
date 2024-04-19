package com.clarusone.poker;

import org.junit.jupiter.api.Test;

import static com.clarusone.poker.HandComparisonUtils.assertWin;

/**
 * Tests wherein the best hand is 4-of-a-kind
 */
public class FourOfAKindTests {

    @Test
    void highest_four_of_a_kind_wins() {
        assertWin("AS AH 2H AD AC", "JS JD JC JH 3D");
    }

    @Test
    void four_of_a_kind_beats_a_full_house() {
        assertWin( "JS JD JC JH AD", "2S AH 2H AS AC");
    }
}
