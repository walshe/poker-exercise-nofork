## Task
Implement the `int compareTo(PokerHand opponentHand)` method of the `PokerHand` class, such that two poker hands
can be compared. This method should return

- `HandResult.WIN` if this hand is better than the opponent's hand 
- `HandResult.LOSS` if the opponent's hand is better 
- `HandResult.TIE` if the result is a tie (AKA a split pot) 

The hands should be ranked according to the rules of [Texas Hold'em](https://en.wikipedia.org/wiki/Texas_hold_%27em).

The `PokerHand` class has a constructor that accepts a string containing 5 cards, e.g.

```java
PokerHand hand = new PokerHand("KS 2H 5C JD TD");
```

The characteristics of the string of cards are:

- Each card consists of two characters, where
  - The first character is the value of the card: `2, 3, 4, 5, 6, 7, 8, 9, T(en), J(ack), Q(ueen), K(ing), A(ce)`
  - The second character represents the suit: `S(pades), H(earts), D(iamonds), C(lubs)`
- A space is used as a separator between cards

In this poker game, Aces are always the highest card in the deck (14), so `"AS KH QC JD TD"` _is_ a straight, but 
`"AS 2H 3C 4D 5D"` _is not_.

### Tests
The repository includes a suite of unit tests that can be used to verify the correctness of your solution. It's 
not feasible to write a test for every possible combination of poker hands, but you are welcome to add additional test
cases to the suite (but please don't remove or modify any of those provided). 

The tests can be run by executing `./gradlew test` (Mac/Linux) or `gradlew.bat test` (Windows) on the command-line 
from the project's root directory.

## Guidelines
The game can be implemented using any combination of the following languages: 
- Java 
- Kotlin
- Groovy 

You are welcome to add any libraries/dependencies to the project, except for Lombok. If you use Kotlin or Groovy, you'll need to modify the 
build to support these languages.

Your solution is not limited to the files in this repository. Feel free to add your own classes, interfaces, enums, records, etc. 

The goal is to pass the test suite. If there isn't a test for a particular case, e.g. an invalid string being passed to the `PokerHand` constructor, you may ignore it.

### Java Version
By default, the project expects to build and run with JDK v21. You are welcome to use a later JDK provided you:
- Make any changes necessary to the Gradle build to support the newer JDK
- Clearly mention in your submssion which JDK version should be used

### GitHub
- Clone or download this repo (do not fork it)
- Push your solution to a private GitHub repo in your account
- When your solution is complete, add your ClarusONE contact as a collaborator to this repo, so they can access it

## Evaluation
While we would ideally like to receive a complete solution i.e. a solution which passes all of the included test cases, 
we're more interested in the quality of the solution than completeness. In other words, a high quality solution that 
omits a few edge cases will receive more credit than a complete, but low-quality solution.

One measure of the quality of a solution is it's flexibility, e.g. if the rules were changed such that two pairs beats three-of-a-kind, how easy is it to make this change?
