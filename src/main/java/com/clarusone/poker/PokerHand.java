package com.clarusone.poker;

import java.util.*;
import java.util.stream.Collectors;

public class PokerHand implements Comparable<PokerHand> {

    /**
     * The PokerHandRank of this hand i.e. ROYAL_FLUSH, STRAIGHT_FLUSH, etc
     */
    private final PokerHandRank pokerHandRank;

    /**
     * The cards in this hand
     * Once initialized we can assume cards are sorted asc by card rank
     */
    private final List<Card> cards;

    public PokerHand(final String fiveCards) {

        this.cards = parseCards(fiveCards);

        //sort the cards ascending by Card rank
        Collections.sort(this.cards, Comparator.comparingInt(card -> card.getCardRank().ordinal()));

        // calculate the rank of the hand
        pokerHandRank = PokerHandRank.fromCards(this.cards);

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
     *
     * @return
     */
    private PokerHandRank getPokerHandRank() {
        return this.pokerHandRank;
    }

    private CardRank getHighestCardRank() {
        return this.cards.stream().max(Comparator.comparingInt(card -> card.getCardRank().ordinal())).get().getCardRank();
    }

    private CardRank getFullHouseHighestThreeOfAKindCardRank() {
        if (getPokerHandRank() != PokerHandRank.FULL_HOUSE) {
            return null;
        }

        // get the rank of the card that has a frequency of 3
        return PokerHandUtils.getCardRankFrequency(this.cards).entrySet().stream()
                .filter(entry -> entry.getValue().equals(3L))
                .map(entry -> entry.getKey()).findFirst().orElseThrow(IllegalStateException::new);

    }

    private CardRank getPairHighestCardRank() {
        if (getPokerHandRank() != PokerHandRank.PAIR) {
            return null;
        }
        // get the rank of the cards that has a rank frequency of 2
        return PokerHandUtils.getCardRankFrequency(this.cards)
                .entrySet().stream()
                .filter(entry -> entry.getValue().equals(2L))
                .map(entry -> entry.getKey()).findFirst().orElseThrow(IllegalStateException::new);


    }

    private CardRank getTwoPairHighestPairCardRank() {
        if (getPokerHandRank() != PokerHandRank.TWO_PAIR) {
            return null;
        }

        // get the ranks of the cards that has a rank frequency of 2, then get max rank of these
        return PokerHandUtils.getCardRankFrequency(this.cards)
                .entrySet().stream()
                .filter(entry -> entry.getValue().equals(2L))
                .map(Map.Entry::getKey)
                .max(Comparator.comparingInt(cardRank -> cardRank.ordinal())).orElseThrow(IllegalStateException::new);
    }

    private CardRank getTwoPairHighestRemainingCardRank() {
        if (getPokerHandRank() != PokerHandRank.TWO_PAIR) {
            return null;
        }

        // get the ranks of the cards that has a rank frequency of 1 i.e. the remaining card
        return PokerHandUtils.getCardRankFrequency(this.cards)
                .entrySet().stream()
                .filter(entry -> entry.getValue().equals(1L))
                .map(entry -> entry.getKey()).findFirst().orElseThrow(IllegalStateException::new);
    }


    @Override
    public int compareTo(PokerHand opponentHand) {
        int comparisonResult = this.getPokerHandRank().compareTo(opponentHand.getPokerHandRank());
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
        if (this.getPokerHandRank() == null || opponentHand.getPokerHandRank() == null) {
            throw new IllegalArgumentException("Invalid Rank");
        }
        if (this.getPokerHandRank() != opponentHand.getPokerHandRank()) {
            throw new IllegalArgumentException("Hands have different ranks!");
        }
        switch (this.getPokerHandRank()) {
            case FULL_HOUSE:
                return this.getFullHouseHighestThreeOfAKindCardRank().compareTo(opponentHand.getFullHouseHighestThreeOfAKindCardRank());

            case HIGH_CARD:
                for (int i = cards.size() - 1; i >= 0; i--) {
                    int highCardResult = this.cards.get(i).getCardRank().compareTo(opponentHand.cards.get(i).getCardRank());

                    // if not a tie, return the result
                    if (highCardResult != 0) {
                        return highCardResult;
                    }
                    //else, continue to the next card
                }
                return 0;

            case PAIR:
                int pairResult = this.getPairHighestCardRank().compareTo(opponentHand.getPairHighestCardRank());
                if (pairResult != 0) {
                    return pairResult;
                } else {
                    // find highest card in the remaining 3 cards
                    for (int i = cards.size() - 3; i >= 0; i--) {
                        pairResult = this.cards.get(i).getCardRank().compareTo(opponentHand.cards.get(i).getCardRank());
                        if (pairResult > 0) {
                            return 1;
                        } else if (pairResult < 0) {
                            return -1;
                        }
                    }
                    return 0;
                }


            case TWO_PAIR:
                int twoPairResult = this.getTwoPairHighestPairCardRank().compareTo(opponentHand.getTwoPairHighestPairCardRank());
                return (twoPairResult != 0) ? twoPairResult : this.getTwoPairHighestRemainingCardRank().compareTo(opponentHand.getTwoPairHighestRemainingCardRank());

            default:
                return this.getHighestCardRank().compareTo(opponentHand.getHighestCardRank());

        }
    }

}
