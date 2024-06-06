package com.clarusone.poker;

import java.util.*;
import java.util.stream.Collectors;

public class PokerHand implements Comparable<PokerHand> {

    private final Rank rank;
    private final List<Card> cards; // TODO do we need to hold field?


    public PokerHand(String fiveCards) {

        this.cards = parseCards(fiveCards);

        // calculate the rank of the hand
        rank = Rank.fromCards(this.cards);


    }

    /**
     * parse the hand into 5 cards, throw an error if any errors during parsing
     *
     * @param fiveCards
     * @return
     */
    private List<Card> parseCards(final String fiveCards) {
        if (fiveCards == null || fiveCards.isEmpty()) {
            throw new IllegalArgumentException("Empty hand");
        }

        String[] cards = fiveCards.split(" ");

        if (cards.length != 5) {
            throw new IllegalArgumentException("Invalid hand");
        }

        return Arrays.stream(cards).map(Card::new).collect(Collectors.toList());
    }


    public Rank getRank() {
        return this.rank;
    }

//    public List<Card> getCards() {
//        return this.cards;
//    }

    public Card.Rank getHighestCardRank() {
        return this.cards.stream().max(Comparator.comparingInt(card -> card.getRank().ordinal())).get().getRank();
    }

    public Card.Rank getFullHouseHighestThreeOfAKindCardRank(){
        if (getRank() != Rank.FULL_HOUSE) {
            return null;
        }
        return null;

    }

    public Card.Rank getHighCardFifthHighestCardRank(){
        if (getRank() != Rank.HIGH_CARD) {
            return null;
        }
        //TODO
        return null;
    }

    public Card.Rank getPairHighestCardRank(){
        if (getRank() != Rank.PAIR) {
            return null;
        }
        //TODO
        return null;
    }

    public Card.Rank getTwoPairHighestRemainingCardRank(){
        if (getRank() != Rank.TWO_PAIR) {
            return null;
        }
        //TODO
        return null;
    }



    @Override
    public int compareTo(PokerHand opponentHand) {

        if (this.rank.compareTo(opponentHand.getRank()) > 0) {
            return 1;
        } else if (this.rank.compareTo(opponentHand.getRank()) < 0) {
            return -1;
        } else {
            // both hands have same rank so we need to anaylze the ranks in finer detail
            // but depending on the rank of hand we need to check on different aspects

            switch (this.rank){
                case FULL_HOUSE :
                    if(this.getFullHouseHighestThreeOfAKindCardRank().compareTo(opponentHand.getFullHouseHighestThreeOfAKindCardRank()) > 0 ){
                        return 1;
                    } else if (this.getFullHouseHighestThreeOfAKindCardRank().compareTo(opponentHand.getFullHouseHighestThreeOfAKindCardRank()) < 0) {
                        return -1;
                    } else {
                        return 0;
                    }

                case HIGH_CARD:
                    if(this.getHighCardFifthHighestCardRank().compareTo(opponentHand.getHighCardFifthHighestCardRank()) > 0 ){
                        return 1;
                    } else if (this.getHighCardFifthHighestCardRank().compareTo(opponentHand.getHighCardFifthHighestCardRank()) < 0) {
                        return -1;
                    } else {
                        return 0;
                    }

                case PAIR:
                    if(this.getPairHighestCardRank().compareTo(opponentHand.getPairHighestCardRank()) > 0 ){
                        return 1;
                    } else if (this.getPairHighestCardRank().compareTo(opponentHand.getPairHighestCardRank()) < 0) {
                        return -1;
                    } else {
                        return 0;
                    }
                case TWO_PAIR:
                    if(this.getTwoPairHighestRemainingCardRank().compareTo(opponentHand.getTwoPairHighestRemainingCardRank()) > 0 ){
                        return 1;
                    } else if (this.getTwoPairHighestRemainingCardRank().compareTo(opponentHand.getTwoPairHighestRemainingCardRank()) < 0) {
                        return -1;
                    } else {
                        return 0;
                    }

                default:
                    if (this.getHighestCardRank().compareTo(opponentHand.getHighestCardRank()) > 0) {
                    return 1;
                } else if (this.getHighestCardRank().compareTo(opponentHand.getHighestCardRank()) < 0) {
                    return -1;
                } else {
                    // if both hands are the same, then we have a tie
                    return 0;
                }

            }


        }

    }

    /**
     * The PokerHand rank enum.
     */
    public enum Rank {
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
         * starting from highest type of hand, try to check if current hand matches
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

            // Sort the cards by rank
            Collections.sort(cards, Comparator.comparingInt(card -> card.getRank().ordinal()));

            boolean isFlush = isFlush(cards);
            boolean isStraight = isStraight(cards);

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

            Map<Card.Rank, Long> rankFrequency = getRankFrequency(cards);

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

        private static boolean isFlush(List<Card> cards) {
            Card.Suit firstSuit = cards.get(0).getSuit();
            return cards.stream().allMatch(card -> card.getSuit() == firstSuit);
        }

        private static boolean isStraight(List<Card> cards) {
            for (int i = 0; i < cards.size() - 1; i++) {
                if (cards.get(i).getRank().ordinal() + 1 != cards.get(i + 1).getRank().ordinal()) {
                    return false;
                }
            }
            return true;
        }

        private static Map<Card.Rank, Long> getRankFrequency(List<Card> cards) {
            return cards.stream()
                    .collect(Collectors.groupingBy(Card::getRank, Collectors.counting()));
        }
    }

}
