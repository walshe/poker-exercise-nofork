package com.clarusone.poker;

import java.util.*;
import java.util.stream.Collectors;

public class PokerHand implements Comparable<PokerHand> {

    private final Rank rank;

    //once initialized we can assume cards are sorted asc by card rank
    private final List<Card> cards;

    /**
     * The PokerHand rank enum.
     * Ordinal of the enum represents the ranking of the hand from 0 (lowest) to 9 (highest)
     */
    enum Rank {
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

        /**
         * Calculate the rank of the hand.
         * Starting from highest type of hand, try to check if current hand matches
         * if not see if it fits next highest hand
         * do until we get to lowest ranking hand.
         * Rank ordinal will represents the poker hand ranking low to high
         *
         * @param cards
         * @return Rank
         */
        public static Rank fromCards(List<Card> cards) {
            if (cards == null || cards.size() != 5) {
                throw new IllegalArgumentException("Exactly 5 cards are required.");
            }

            boolean isFlush = Utils.isFlush(cards);
            boolean isStraight = Utils.isStraight(cards);

            if (isFlush && isStraight && cards.get(4).getRank() == Card.Rank.ACE) {
                return ROYAL_FLUSH;
            }

            if (isFlush && isStraight) {
                return STRAIGHT_FLUSH;
            }

            if (isFlush) {
                return FLUSH;
            }

            if (isStraight) {
                return STRAIGHT;
            }

            Map<Card.Rank, Long> rankFrequency = Utils.getRankFrequency(cards);

            if (rankFrequency.containsValue(4L)) {
                return FOUR_OF_A_KIND;
            }

            if (rankFrequency.containsValue(3L) && rankFrequency.containsValue(2L)) {
                return FULL_HOUSE;
            }

            if (rankFrequency.containsValue(3L)) {
                return THREE_OF_A_KIND;
            }

            if (Collections.frequency(new ArrayList<>(rankFrequency.values()), 2L) == 2) {
                return TWO_PAIR;
            }

            if (rankFrequency.containsValue(2L)) {
                return PAIR;
            }

            return HIGH_CARD;
        }

    }


    public PokerHand(String fiveCards) {

        this.cards = parseCards(fiveCards);

        //sort the cards ascending by Card rank
        Collections.sort(this.cards, Comparator.comparingInt(card -> card.getRank().ordinal()));

        // calculate the rank of the hand
        rank = Rank.fromCards(this.cards);


    }

    /**
     * Parse the hand into 5 card objects, throw an error if any errors during parsing
     *
     * @param fiveCards
     * @return
     */
    private List<Card> parseCards(final String fiveCards) {
        return Optional.ofNullable(fiveCards)
                .filter(hand -> !hand.isBlank())
                .map(hand -> hand.split(" "))
                .filter(parts -> parts.length == 5)
                .map(Arrays::stream)
                .orElseThrow(() -> new IllegalArgumentException("Invalid hand"))
                .map(Card::new)
                .collect(Collectors.toList());
    }

    /**
     * Get the rank of the hand. e.g. STRAIGHT_FLUSH, PAIR, THREE_OF_A_KIND, etc.
     * @return
     */
    private Rank getRank() {
        return this.rank;
    }

    private Card.Rank getHighestCardRank() {
        return this.cards.stream().max(Comparator.comparingInt(card -> card.getRank().ordinal())).get().getRank();
    }

    private Card.Rank getFullHouseHighestThreeOfAKindCardRank() {
        if (getRank() != Rank.FULL_HOUSE) {
            return null;
        }

        // get the rank of the card that has a frequency of 3
        return Utils.getRankFrequency(this.cards).entrySet().stream()
                .filter(entry -> entry.getValue().equals(3L))
                .map(entry -> entry.getKey()).findFirst().orElseThrow(IllegalStateException::new);

    }

    private Card.Rank getPairHighestCardRank() {
        if (getRank() != Rank.PAIR) {
            return null;
        }
        // get the rank of the cards that has a rank frequency of 2
        return Utils.getRankFrequency(this.cards)
                .entrySet().stream()
                .filter(entry -> entry.getValue().equals(2L))
                .map(entry -> entry.getKey()).findFirst().orElseThrow(IllegalStateException::new);


    }

    private Card.Rank getTwoPairHighestRemainingCardRank() {
        if (getRank() != Rank.TWO_PAIR) {
            return null;
        }
        // get the ranks of the cards that has a rank frequency of 1 i.e. the remaining card
        return Utils.getRankFrequency(this.cards)
                .entrySet().stream()
                .filter(entry -> entry.getValue().equals(1L))
                .map(entry -> entry.getKey()).findFirst().orElseThrow(IllegalStateException::new);
    }


    @Override
    public int compareTo(PokerHand opponentHand) {
        int comparisonResult = this.getRank().compareTo(opponentHand.getRank());
        return (comparisonResult == 0) ? compareToRankDetailed(opponentHand) : comparisonResult;
    }

    /**
     * Compare two hands of the same rank.
     * Depending on the rank of hand we need to check on different aspects
     *
     * @param opponentHand
     * @return
     */
    private int compareToRankDetailed(PokerHand opponentHand) {
        if (this.getRank() == null || opponentHand.getRank() == null) {
            throw new IllegalArgumentException("Invalid Rank");
        }
        if (this.getRank() != opponentHand.getRank()) {
            throw new IllegalArgumentException("Hands have different ranks!");
        }
        switch (this.rank) {
            case FULL_HOUSE:
                return this.getFullHouseHighestThreeOfAKindCardRank().compareTo(opponentHand.getFullHouseHighestThreeOfAKindCardRank());

            case HIGH_CARD:
                for (int i = cards.size() - 1; i >= 0; i--) {
                    int comparisonResult = this.cards.get(i).getRank().compareTo(opponentHand.cards.get(i).getRank());

                    // if not a tie, return the result
                    if (comparisonResult != 0) {
                        return comparisonResult;
                    }
                    //else, continue to the next card
                }
                return 0;

            case PAIR:
                int comparisonResult = this.getPairHighestCardRank().compareTo(opponentHand.getPairHighestCardRank());
                if (comparisonResult != 0) {
                    return comparisonResult;
                } else {
                    // find highest card in the remaining cards
                    for (int i = cards.size() - 3; i >= 0; i--) {
                        comparisonResult = this.cards.get(i).getRank().compareTo(opponentHand.cards.get(i).getRank());
                        if (comparisonResult > 0) {
                            return 1;
                        } else if (comparisonResult < 0) {
                            return -1;
                        }
                    }
                    return 0;
                }


            case TWO_PAIR:
                return this.getTwoPairHighestRemainingCardRank().compareTo(opponentHand.getTwoPairHighestRemainingCardRank());


            default:
                return this.getHighestCardRank().compareTo(opponentHand.getHighestCardRank());


        }
    }

}
