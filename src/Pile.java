import java.util.ArrayList;

/**
 * Hold the cards a player has already played
 */
public class Pile {
    private ArrayList<Card> cards = new ArrayList<>();;

    /**
     * Returns the ArrayList of cards contained in the Pile
     */
    ArrayList<Card> getCards(){
        return cards;
    }

    /**
     * Adds a card to the pile.
     */
    void addCard(Card card){
        cards.add(card);
    }

    /**
     * Returns the card that has most recently been added to the Pile.
     */
    Card getTopCard(){
        return cards.get(cards.size()-1);
    }

    /**
     * Returns boolean indicating whether the Pile is empty or not.
     */
    boolean isEmpty(){
        return cards.isEmpty();
    }

    /**
     * Returns an ArrayList of empty piles. One for each player.
     * Used to set up the game.
     */
    ArrayList<Pile> startingPlayedPiles (int numPlayers){
        ArrayList<Pile> startingPiles = new ArrayList<Pile>();
        for (int i = 0; i<numPlayers; i++){
            Pile p = new Pile();
            startingPiles.add(p);
        }
        return startingPiles;
    }






}

