/**
 * Represents a standard card of a certain suit and value, called "rank".
 */
public class Card {
    private Suit suit;
    private Rank rank;


    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }

    /**
     * Returns suit of card.
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Returns rank of card
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Returns readable String displaying Card's properties.
     */
    @Override
    public String toString() {
        return rank + " of " + suit;
    }

    /**
     * Checks if this card has matching suit and/or rank with another card.
     * Which property is checked is based on snapCondition parameter.
     */
    public boolean matches(Card otherCard, int snapCondition) {
        switch (snapCondition) {
            case 0:
                return this.suit == otherCard.suit;

            case 1:
                return this.rank == otherCard.rank;

            case 2:
                return (this.rank == otherCard.rank || this.suit == otherCard.suit);

            default:
                throw new IllegalArgumentException("Invalid snap condition");
        }
    }

}
