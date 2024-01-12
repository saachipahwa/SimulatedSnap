import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Main interface, used to run the Snap game
 * @author Saachi Pahwa
 */

public class Main {
    /**
     * Runs the main functionality for the game
     * @throws InterruptedException if thread is interrupted while sleeping
     */
    public static void main(String[] args) throws InterruptedException {
        // Establish game conditions with user
        Scanner scanner = new Scanner(System.in);
        System.out.println("Get ready to watch some snap!");

        //Condition 1
        int numPlayers = getNumPlayers(scanner);

        //Condition 2
        int stopCondition = getStopCondition(scanner);

        //Handle different responses to stop condition question
        int numRounds = 0;
        if (stopCondition==1){
            numRounds = getNumRounds(scanner);
        }
        else if (stopCondition==0){
            System.out.println("Got it.");
        }
        else{
            throw new IllegalArgumentException("You did not type 0 or 1.");
        }

        //Condition 3
        int snapCondition = getSnapCondition(scanner);
        System.out.println("The game has been set up. Thank you for your help!");

        //Set up deck
        ArrayList<Hand> playerHands = setDeck(numPlayers);

        //Set up piles to hold cards that have been played
        Pile p = new Pile();
        ArrayList<Pile> playedPiles = p.startingPlayedPiles(numPlayers);

        // Set up random reaction times
        Random[] reactionTimesRand = new Random[numPlayers];
        for (int a = 0; a < numPlayers; a++){
            Random r = new Random();
            reactionTimesRand[a] = r;
        }

        //Uses boolean 'playing' to keep track of when game should end...
        //...because stopping conditions can vary based on user's answer to question
        boolean playing = true;
        int round = 0;
        int player = 0;

        while (playing) {
            for (player = 0; player < numPlayers; player++) {
                //Play card
                if (playerHands.get(player).isEmpty()){
                    System.out.println("Player " + player + " has no cards. Skip their turn!");
                    continue;
                }

                Card newCard = playCard(player,playerHands.get(player), playedPiles.get(player));

                //Check for SNAP
                boolean snap = false;
                for (int j = 0; j < numPlayers; j++) {
                    if (j != player){
                        if (!playedPiles.get(j).isEmpty() && newCard.matches(playedPiles.get(j).getTopCard(), snapCondition)){
                            snap = true;
                            break;
                        }
                    }
                }
                if (snap) {
                    //If cards match then let's see who shouts the fastest!
                    int fastestPlayer = whoSnapsFirst(reactionTimesRand, numPlayers);

                    //Add played piles to winner's hand and check if they now have all the cards
                    for (int k = 0; k < numPlayers; k++) {
                        playerHands.get(fastestPlayer).addCards(playedPiles.get(k).getCards());

                        if (playerHands.get(fastestPlayer).getSize()>=52){
                            if(stopCondition==0){ //stop game if stop condition says to
                                System.out.println("Player " + fastestPlayer + " has won! What a great game.");
                                playing = false;
                                break;
                            }
                            else {
                                //If someone has all the cards, reshuffle deck
                                playerHands = setDeck(numPlayers);
                            }
                        }
                    }
                }
            }

            //If stop condition says so, check if we have reached max number of rounds and stop game if we have
            round+=1;
            if (stopCondition==1 && round>=numRounds){
                System.out.println("All rounds have been completed.");
                getWinningPlayer(numPlayers, playerHands);
                playing = false;
                break;
            }
        }

        System.out.println(("Thank you for joining us while we play Snap! Have a great day."));

    }

    /**
     * Asks user how many players should participate in the game. Returns user's answer.
     * This is the first introductory question of 3.
     */
    public static int getNumPlayers(Scanner scanner) {
        //Get number of players
        System.out.println("How many players are playing today?");
        int numPlayers = scanner.nextInt();
        if (numPlayers==1) {
            throw new IllegalArgumentException("We need more than 1 player!");

        }
        else if (numPlayers<=5 && numPlayers > 1){
            return numPlayers;
        }
        else{
            throw new IllegalArgumentException("Too many players for one deck of cards. Sorry!");
        }
    }

    /**
     * Asks user if the game should end when someone has all the cards
     * Or after a certain number of rounds. Returns user's answer.
     * This is the second introductory question of 3.
     * Different potential answers are handled in Main.
     */
    public static int getStopCondition(Scanner scanner) {
        //Get stop condition (= when should game end?)
        System.out.println("Would you like to stop when someone has all the cards (0) or play a certain number of rounds (1)? \n Type the corresponding number.");
        int stopCondition = scanner.nextInt();
        if (stopCondition==0 || stopCondition==1){
            return stopCondition;
        }
        else{
            throw new IllegalArgumentException("Too many players for one deck of cards. Sorry!");
        }
    }

    /**
     * Asks user how many rounds of Snap should be played. Returns user's answer.
     * This question is only asked if the user asks for this stop condition.
     */
    public static int getNumRounds(Scanner scanner) {
        System.out.println("Then we will just play as many rounds as you want.  How many rounds would you like us to play?");
        int numRounds = scanner.nextInt();
        if(numRounds < 50){
            return numRounds;}
        else {
            throw new IllegalArgumentException("Too many rounds! Sorry!");
        }
    }

    /**
     * Asks user whether players should snap based on the card suit, number or both.
     * Returns the user's answer.
     * This is the third introductory question of 3.
     */
    public static int getSnapCondition(Scanner scanner) {
        //Get snap condition
        System.out.println("Should we snap on suit (0), number (1), or both (2)? Type the corresponding number.");
        int snapCondition = scanner.nextInt();
        if (snapCondition == 0 || snapCondition == 1 || snapCondition == 2){
            return snapCondition;
        }
        else {
            throw new IllegalArgumentException("Too many rounds! Sorry!");
        }
    }

    /**
     * Creates deck, shuffles and splits into equal parts for each user's hand.
     * Returns an ArrayList containing a Hand of cards for each user.
     */
    public static ArrayList<Hand> setDeck(int numPlayers) throws InterruptedException {
        Deck deck = new Deck();
        System.out.println("Shuffling deck.");
        deck.shuffle();
        Thread.sleep(1000); // Sleep for realism since shuffling takes time

        //Split deck into equal hands for each player
        ArrayList<Hand> playerHands = deck.splitDeck(numPlayers);
        return playerHands;
    }

    /**
     * Plays card from user's hand and puts it into their pile of played cards.
     * Returns the card they played.
     */
    public static Card playCard(int player, Hand playerHand, Pile playedPile) throws InterruptedException {
        Thread.sleep(1000); //Sleep for realism
        Card newCard = playerHand.deal();

        playedPile.addCard(newCard); //add card to their played pile
        System.out.println("Player " + player + " played " + newCard.toString());
        return newCard;
    }

    /**
     * Generates a random number of milliseconds between 500 and 1000 for each user.
     * The player with the smallest number "snaps" first and puts all the Piles in their Hand.
     * Returns the int corresponding with this user.
     */
    public static int whoSnapsFirst(Random[] reactionTimesRand, int numPlayers) throws InterruptedException {
        int fastestPlayer = 0;
        int fastestTime = 1001;
        for (int j = 0; j < numPlayers; j++) {
            int newTime = reactionTimesRand[j].nextInt(500, 1000);

            if (newTime < fastestTime) {
                fastestTime = newTime;
                fastestPlayer = j;
            }
        }
        Thread.sleep(fastestTime);
        System.out.println("Player " + fastestPlayer + " shouts SNAP!");
        return fastestPlayer;
    }

    /**
     * Judges which user has the most cards.
     * Returns ints corresponding to the winning player and the size of their Hand.
     */
    public static void getWinningPlayer(int numPlayers, ArrayList<Hand> playerHands){
        int winningHandSize = 0;
        int winningPlayer = 0;

        //What is the maximum amount of cards in a player's hand?
        for (int b = 0; b < numPlayers; b++) {
            int size = playerHands.get(b).getSize();
            if (size>winningHandSize){
                winningHandSize=size;
                winningPlayer=b;
            }
        }

        //Did more than one player have the maximum amount of cards?
        int count = 0;
        for (int b = 0; b < numPlayers; b++) {
            int size = playerHands.get(b).getSize();
            if (size==winningHandSize){
                count+=1;
            }
        }

        //If so, it's a draw!
        if (count>1){
            System.out.println("It's a draw! What a great game.");
        }
        else{
            System.out.println("Player " + winningPlayer + " has won with " + winningHandSize + " cards! What a great game.");
        }
    }
}