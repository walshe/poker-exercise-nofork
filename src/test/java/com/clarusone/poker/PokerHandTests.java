package com.clarusone.poker;

import org.junit.jupiter.api.Test;

import static com.clarusone.poker.HandComparisonUtils.assertTie;
import static com.clarusone.poker.HandComparisonUtils.assertWin;

public class PokerHandTests {

    @Test
    public void highest_straight_flush_wins() {
        assertWin("KS AS TS QS JS", "2H 3H 4H 5H 6H");
    }

    @Test
    public void straight_flush_beats_4_of_a_kind() {
        assertWin("2H 3H 4H 5H 6H", "AS AD AC AH JD");
    }

    @Test
    public void highest_4_of_a_kind_wins() {
        assertWin("AS AH 2H AD AC", "JS JD JC JH 3D");
    }

    @Test
    public void four_of_a_kind_beats_a_full_house() {
        assertWin( "JS JD JC JH AD", "2S AH 2H AS AC");
    }

    @Test
    public void full_house_beats_a_flush() {
        assertWin("2S AH 2H AS AC", "2H 3H 5H 6H 7H");
    }

    @Test
    public void highest_flush_wins() {
        assertWin("AS 3S 4S 8S 2S", "2H 3H 5H 6H 7H");
    }

    @Test
    public void flush_beats_a_straight() {
        assertWin("2H 3H 5H 6H 7H", "2S 3H 4H 5S 6C");
    }

    @Test
    public void two_straights_with_same_highest_card_tie() {
        assertTie("2S 3H 4H 5S 6C", "3D 4C 5H 6H 2S");
    }

    @Test
    public void straight_beats_three_of_a_kind() {
        assertWin("2S 3H 4H 5S 6C", "AH AC 5H 6H AS");
    }

    @Test
    public void three_of_a_kind_beats_two_pairs() {
        assertWin("AH AC 5H 6H AS", "2S 2H 4H 5S 4C");
    }

    @Test
    public void two_pairs_beats_a_single_pair() {
        assertWin("2S 2H 4H 5S 4C", "AH AC 5H 6H 7S");
    }

    @Test
    public void highest_pair_wins() {
        assertWin("AH AC 5H 6H 7S", "6S AD 7H 4S AS");
    }

    @Test
    public void pair_beats_a_high_card() {
        assertWin("AH AC 5H 6H 7S", "2S AH 4H 5S KC");
    }

    @Test
    public void lowest_card_loses() {
        assertWin("7H 3C TH 6H 9S", "2S 3H 6H 7S 9C");
    }

    @Test
    public void highest_card_wins() {
        assertWin("4S 5H 6H TS AC", "3S 5H 6H TS AC");
    }

    @Test
    public void equal_cards_tie() {
        assertTie("2S AH 4H 5S 6C", "AD 4C 5H 6H 2C");
    }

    @Test
    public void two_full_houses_highest_three_of_a_kind_wins() {
        assertWin("KS KH KD 2S 2C", "JS JH JD AS AC");
    }

    @Test
    public void two_pairs_highest_remaining_card_wins() {
        assertWin("KS KD QD QC 5C", "KS KH QD QS 2C");
    }

    @Test
    public void two_pairs_highest_pair_wins() {
        assertWin("QD QS AS AH 2C", "KS KD QD QC 5C");
    }

    @Test
    public void royal_flush_wins() {
        assertWin("AS QS TS KS JS", "KD QD JD TD 9D");
    }

    @Test
    public void royal_flush_tie() {
        assertTie("AS QS TS KS JS", "AH QH TH KH JH");
    }

    @Test
    public void royal_flush_beats_four_of_a_kind() {
        assertWin("AS QS TS KS JS", "7H 7C 7D 7S JH");
    }
}

