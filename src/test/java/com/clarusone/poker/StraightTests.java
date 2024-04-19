package com.clarusone.poker;

import org.junit.jupiter.api.Test;

import static com.clarusone.poker.HandComparisonUtils.assertTie;
import static com.clarusone.poker.HandComparisonUtils.assertWin;

/**
 * Tests wherein the best hand is a straight (5 consecutive cards)
 */
public class StraightTests {

    @Test
    void two_straights_with_same_highest_card_tie() {
        assertTie("2S 3H 4H 5S 6C", "3D 4C 5H 6H 2S");
    }

    @Test
    void straight_beats_three_of_a_kind() {
        assertWin("2S 3H 4H 5S 6C", "AH AC 5H 6H AS");
    }
}
