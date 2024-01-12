import java.util.ArrayList;
import java.util.Collections;

/**
 * Standard deck of 52 cards.
 */
public class Deck {
    private ArrayList<Card> cards;

    /**
     * Fills deck with 52 cards of different rank and suit.
     */
    public Deck() {
        //Create all cards
        cards = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                cards.add(new Card(suit, rank));
            }
        }
    }

    /**
     * Shuffles order of cards in the deck.
     */
    public void shuffle() {
        Collections.shuffle(cards);
    }

    /**
     * Separates deck into equal Hands. One Hand for each player.
     * Returns an ArrayList of these Hands.
     */
    ArrayList<Hand> splitDeck(int numPlayers) {
        ArrayList<Hand> playerDecks = new ArrayList<>();
        int cardsPerPlayer = cards.size() / numPlayers;

        // Split cards evenly
        for (int i = 0; i < numPlayers; i++) {
            Hand playerD = new Hand();
            for (int j = 0; j < cardsPerPlayer; j++) {
                playerD.addCard(cards.remove(0));
            }
            playerDecks.add(playerD);
        }

        // Add any remaining cards to last player
        if (!cards.isEmpty()) {
            Hand lastPlayer = playerDecks.get(playerDecks.size() - 1);
            lastPlayer.addCards(cards);
        }

        return playerDecks;
    }
    
}
