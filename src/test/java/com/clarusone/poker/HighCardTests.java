package com.clarusone.poker;

import org.junit.jupiter.api.Test;

import static com.clarusone.poker.HandComparisonUtils.assertTie;
import static com.clarusone.poker.HandComparisonUtils.assertWin;

/**
 * Tests wherein the best hand is a high card
 */
public class HighCardTests {

    @Test
    public void first_highest_card_wins() {
        assertWin("7H 3C TH 6H 9S", "2S 3H 6H 7S 9C");
    }

    @Test
    public void fifth_highest_card_wins() {
        assertWin("4S 5H 6H TS AC", "3S 5H 6H TS AC");
    }

    @Test
    public void equal_cards_tie() {
        assertTie("2S AH 4H 5S 6C", "AD 4C 5H 6H 2C");
    }
}
