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

    private Map<CardRank, Long> cardRankFrequency;

    public PokerHand(final String fiveCards) {

        this.cards = parseCards(fiveCards);

        //sort the cards ascending by Card rank
        Collections.sort(this.cards, Comparator.comparingInt(card -> card.getCardRank().ordinal()));

        // calculate the card rank frequency map which is used to calculate the rank of the hand
        cardRankFrequency = cards.stream()
                .collect(Collectors.groupingBy(Card::getCardRank, Collectors.counting()));

        // calculate the rank of the hand
        pokerHandRank = calculatePokerHandRank();

    }

    /**
     * Parse the hand into 5 card objects, throw an error if any errors during parsing
     *
     * @param fiveCards
     * @return a list of five card objects
     */
    private List<Card> parseCards(final String fiveCards) {
        return Optional.ofNullable(fiveCards)
                .filter(hand -> !hand.isBlank())
                .map(hand -> hand.split(" "))
                .filter(parts -> parts.length == 5)
                .map(Arrays::stream)
                .orElseThrow(() -> new IllegalArgumentException("Invalid hand. Must be a space delimited String of 5 cards e.g. \"2S 2H 4H 5S 4C\""))
                .map(Card::new)
                .collect(Collectors.toList());
    }

    /**
     * Calculate the rank of the hand e.g. STRAIGHT_FLUSH, FLUSH, etc
     *
     * @return the rank of the hand
     */
    private PokerHandRank calculatePokerHandRank() {
        boolean isFlush = isFlush();
        boolean isStraight = isStraight();

        if (isFlush && isStraight && cards.get(4).getCardRank() == CardRank.ACE) {
            return PokerHandRank.ROYAL_FLUSH;
        }

        if (isFlush && isStraight) {
            return PokerHandRank.STRAIGHT_FLUSH;
        }

        if (isFlush) {
            return PokerHandRank.FLUSH;
        }

        if (isStraight) {
            return PokerHandRank.STRAIGHT;
        }

        if (cardRankFrequency.containsValue(4L)) {
            return PokerHandRank.FOUR_OF_A_KIND;
        }

        if (cardRankFrequency.containsValue(3L) && cardRankFrequency.containsValue(2L)) {
            return PokerHandRank.FULL_HOUSE;
        }

        if (cardRankFrequency.containsValue(3L)) {
            return PokerHandRank.THREE_OF_A_KIND;
        }

        if (Collections.frequency(new ArrayList<>(cardRankFrequency.values()), 2L) == 2) {
            return PokerHandRank.TWO_PAIR;
        }

        if (cardRankFrequency.containsValue(2L)) {
            return PokerHandRank.PAIR;
        }

        return PokerHandRank.HIGH_CARD;
    }

    /**
     * Check if the cards are a flush - all same Card Suit
     *
     * @return
     */
    private boolean isFlush() {
        CardSuit firstSuit = cards.get(0).getCardSuit();
        return cards.stream().allMatch(card -> card.getCardSuit() == firstSuit);
    }

    /**
     * Check if the cards are a straight.
     *
     * @return
     */
    private boolean isStraight() {
        for (int i = 0; i < cards.size() - 1; i++) {
            if (cards.get(i).getCardRank().ordinal() + 1 != cards.get(i + 1).getCardRank().ordinal()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets highest card rank in the hand
     *
     * @return the card rank
     */
    private CardRank getHighestCardRank() {
        return this.cards.stream().max(Comparator.comparingInt(card -> card.getCardRank().ordinal())).get().getCardRank();
    }

    /**
     * Gets the rank of the three of a kind
     *
     * @return the card rank
     */
    private CardRank getFullHouseHighestThreeOfAKindCardRank() {
        if (this.pokerHandRank != PokerHandRank.FULL_HOUSE) {
            throw new IllegalStateException("Unexpected PokerHandRank");
        }

        // get the rank of the card that has a frequency of 3
        return cardRankFrequency.entrySet().stream()
                .filter(entry -> entry.getValue().equals(3L))
                .map(entry -> entry.getKey()).findFirst().orElseThrow(() -> new IllegalStateException("Three of a kind should have been found"));

    }

    /**
     * Gets the card rank of the pair
     *
     * @return the card rank
     */
    private CardRank getPairHighestCardRank() {
        if (this.pokerHandRank != PokerHandRank.PAIR) {
            throw new IllegalStateException("Unexpected PokerHandRank");
        }
        // get the rank of the cards that has a rank frequency of 2
        return cardRankFrequency
                .entrySet().stream()
                .filter(entry -> entry.getValue().equals(2L))
                .map(entry -> entry.getKey()).findFirst().orElseThrow(IllegalStateException::new);


    }

    /**
     * Gets the card rank of the highest pair
     *
     * @return the card rank
     */
    private CardRank getTwoPairHighestPairCardRank() {
        if (this.pokerHandRank != PokerHandRank.TWO_PAIR) {
            throw new IllegalStateException("Unexpected PokerHandRank");
        }

        // get the ranks of the cards that has a rank frequency of 2, then get max rank of these
        return cardRankFrequency
                .entrySet().stream()
                .filter(entry -> entry.getValue().equals(2L))
                .map(Map.Entry::getKey)
                .max(Comparator.comparingInt(cardRank -> cardRank.ordinal())).orElseThrow(IllegalStateException::new);
    }

    /**
     * Gets the highest remaining card rank in a two pair hand.
     *
     * @return the card rank
     */
    private CardRank getTwoPairHighestRemainingCardRank() {
        if (this.pokerHandRank != PokerHandRank.TWO_PAIR) {
            throw new IllegalStateException("Unexpected PokerHandRank");
        }

        // get the ranks of the cards that has a rank frequency of 1 i.e. the remaining card
        return cardRankFrequency
                .entrySet().stream()
                .filter(entry -> entry.getValue().equals(1L))
                .map(entry -> entry.getKey()).findFirst().orElseThrow(IllegalStateException::new);
    }

    /**
     * Checks whose has highest card. If cards are equal then check remaining cards for highest rank
     *
     * @param opponentHand
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     */
    private int compareHighCard(PokerHand opponentHand) {
        for (int i = cards.size() - 1; i >= 0; i--) {
            int result = this.cards.get(i).getCardRank().compareTo(opponentHand.cards.get(i).getCardRank());
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    /**
     * Checks whose pair is higher. If pairs are equal then check remaining cards for hughest rank
     *
     * @param opponentHand
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     */
    private int comparePair(PokerHand opponentHand) {
        int result = this.getPairHighestCardRank().compareTo(opponentHand.getPairHighestCardRank());
        if (result != 0) {
            return result;
        }
        // Check remaining cards if pairs are the same
        for (int i = cards.size() - 3; i >= 0; i--) {
            result = this.cards.get(i).getCardRank().compareTo(opponentHand.cards.get(i).getCardRank());
            if (result != 0) {
                return result;
            }
        }
        return 0;
    }

    /**
     * Getter for PokerHandRank
     *
     * @return the pokerHandRank
     */
    public PokerHandRank getPokerHandRank() {
        return pokerHandRank;
    }

    @Override
    public int compareTo(PokerHand opponentHand) {
        int comparisonResult = this.pokerHandRank.compareTo(opponentHand.getPokerHandRank());
        return (comparisonResult == 0) ? compareToRankDetailed(opponentHand) : comparisonResult;
    }

    /**
     * Compare two hands of the same rank.
     * Depending on the rank of hand we need to check on different aspects of the poker hand
     *
     * @param opponentHand
     * @return a negative integer, zero, or a positive integer as this object
     * is less than, equal to, or greater than the specified object.
     */
    private int compareToRankDetailed(PokerHand opponentHand) {
        if (this.pokerHandRank == null || opponentHand.getPokerHandRank() == null) {
            throw new IllegalStateException("Invalid Rank");
        }
        if (this.pokerHandRank != opponentHand.getPokerHandRank()) {
            throw new IllegalStateException("Hands have different ranks!");
        }
        return switch (this.pokerHandRank) {
            case FULL_HOUSE ->
                    this.getFullHouseHighestThreeOfAKindCardRank().compareTo(opponentHand.getFullHouseHighestThreeOfAKindCardRank());
            case HIGH_CARD -> compareHighCard(opponentHand);
            case PAIR -> comparePair(opponentHand);
            case TWO_PAIR -> {
                int twoPairResult = this.getTwoPairHighestPairCardRank().compareTo(opponentHand.getTwoPairHighestPairCardRank());
                yield (twoPairResult != 0) ? twoPairResult : this.getTwoPairHighestRemainingCardRank().compareTo(opponentHand.getTwoPairHighestRemainingCardRank());
            }
            default -> this.getHighestCardRank().compareTo(opponentHand.getHighestCardRank());
        };
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PokerHand pokerHand = (PokerHand) o;
        return Objects.equals(cards, pokerHand.cards);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(cards);
    }
}
