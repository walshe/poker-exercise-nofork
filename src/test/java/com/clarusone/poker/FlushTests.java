package com.clarusone.poker;

import org.junit.jupiter.api.Test;

import static com.clarusone.poker.HandComparisonUtils.assertWin;

/**
 * Tests wherein the best hand is a flush
 */
public class FlushTests {

    @Test
    void highest_flush_wins() {
        assertWin("AS 3S 4S 8S 2S", "2H 3H 5H 6H 7H");
    }

    @Test
    void flush_beats_a_straight() {
        assertWin("2H 3H 5H 6H 7H", "2S 3H 4H 5S 6C");
    }
}
