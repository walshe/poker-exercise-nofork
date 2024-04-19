package com.clarusone.poker;

import org.junit.jupiter.api.Test;

import static com.clarusone.poker.HandComparisonUtils.assertWin;

/**
 * Tests wherein the best hand is 3-of-a-kind
 */
public class ThreeOfAKindTests {

    @Test
    void three_of_a_kind_beats_two_pairs() {
        assertWin("AH AC 5H 6H AS", "2S 2H 4H 5S 4C");
    }
}
