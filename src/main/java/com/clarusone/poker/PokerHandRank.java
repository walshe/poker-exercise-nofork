package com.clarusone.poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * The PokerHand rank enum.
 * Ordinal of the enum represents the ranking of the hand from 0 (lowest) to 9 (highest)
 */
enum PokerHandRank {
    HIGH_CARD,
    PAIR,
    TWO_PAIR,
    THREE_OF_A_KIND,
    STRAIGHT,
    FLUSH,
    FULL_HOUSE,
    FOUR_OF_A_KIND,
    STRAIGHT_FLUSH,
    ROYAL_FLUSH;
}
