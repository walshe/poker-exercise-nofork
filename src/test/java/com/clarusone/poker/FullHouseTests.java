package com.clarusone.poker;

import org.junit.jupiter.api.Test;

import static com.clarusone.poker.HandComparisonUtils.assertWin;

/**
 * Tests wherein the best hand is a full house (2 of one kind, and 3 of another)
 */
public class FullHouseTests {

    @Test
    void full_house_beats_a_flush() {
        assertWin("2S AH 2H AS AC", "2H 3H 5H 6H 7H");
    }

    @Test
    void two_full_houses_highest_three_of_a_kind_wins() {
        assertWin("KS KH KD 2S 2C", "JS JH JD AS AC");
    }
}
