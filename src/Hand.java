import java.util.ArrayList;

/**
 * Hold the cards a player has yet to play
 */
public class Hand {
    private ArrayList<Card> cards = new ArrayList<>();;

    /**
     * Adds a card to the Hand.
     */
    void addCard(Card card){
        cards.add(card);
    }

    /**
     * Adds multiple cards to the hand.
     */
    void addCards(ArrayList<Card> newCards){
        cards.addAll(newCards);
    }

    /**
     * "Deals" the top card from the Hand.
     */
    public Card deal() {
        if (cards.isEmpty()) {
            return null; // Or throw an exception if you prefer
        }
        return cards.remove(0);
    }

    /**
     * Returns an int representing the size of the Hand.
     */
    int getSize(){
        return cards.size();
    }

    /**
     * Returns a boolean indicating whether the Hand is empty.
     */
    boolean isEmpty(){
        return cards.isEmpty();
    }






}