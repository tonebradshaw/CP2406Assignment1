import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by tony on 12/08/2016.
 */

public class Play {

    static String [] playerNames;
    static String [] playerOrder;
    static ArrayList <Card> discardedCards;
    static Scanner input;
    static Player playerOne;
    static Player playerTwo;
    static Player playerThree;
    static Player playerFour;
    static Player playerFive;
    static int numberOfPlayers;
    static String playerNameOne;
    static String playerNameTwo;
    static String playerNameThree;
    static String playerNameFour;
    static String playerNameFive;
    static int playerNumber, playerTurn, turn;
    static int playerPosition, rounds;
    static int handCards;
    static String entry;
    static String playerSequence;

    static ArrayList<Card> shuffledDeck = new ArrayList<>();

    static String [] cleavageHierarchy = {"none", "poor/none", "1 poor", "2 poor", "1 good", "1 good, 1 poor", "2 good", "3 good",
            "1 perfect", "1 perfect, 1 good", "1 perfect, 2 good", "2 perfect, 1 good", "3 perfect", "4 perfect", "6 perfect"};
    static String [] crustalAbundanceHierarchy = {"ultratrace", "trace", "low", "moderate", "high", "very high"};
    static String [] economicValueHierarchy = {"trivial", "low", "moderate", "high", "very high", "I'm rich!"};

    public static void main(String[] args) {

        Deck deck = new Deck();

        for (int i = 0; i < 60; ++i) { //convert Deck array to shuffledDeck ArrayList

            shuffledDeck.add(deck.cards[i]);
        }

        Collections.shuffle(shuffledDeck); //shuffle the card deck

        //input - select how many players
        do { //check input until an integer between 3 and 5 is entered
            try {
                System.out.print("Enter number of players (3-5): ");
                Scanner input = new Scanner(System.in);
                numberOfPlayers = input.nextInt();

            } catch (Exception e) { //catch an input that isn't an integer
                System.out.print("You must enter an Integer - ");
            }

        }while(numberOfPlayers != 3 && numberOfPlayers != 4 && numberOfPlayers != 5);

        playerNames = new String [numberOfPlayers];
        playerOrder = new String [numberOfPlayers];
        discardedCards = new ArrayList<>();
        turn = 0;
        rounds = 0;

        switch(numberOfPlayers){ //build selected number of hands of 8 cards

            case 3: //create 3 player's hands

                enterFirstThreePlayersNames(); //enter first three player's names

                playerNumber = (int)(Math.random() * numberOfPlayers); //randomly choose player 1, the following players are in order of entry
                playerPosition = playerNumber;

                buildFirstThreePlayers();

                for(int i=0; i<8; ++i){ //add 8 cards to each hand and remove those cards from deck

                    fillThreeHands();
                }
                if(rounds == 0) {
                    displayPlayerSequence();
                    playerWaitToStart();
                    selectPlayerOneCard();
                    rounds = 1;
                }
                //playerWaitToStart();
                //selectPlayerOneCard();

                break;

            case 4: // create 4 player's hands

                enterFirstThreePlayersNames(); //enter first three player's names

                System.out.print("Enter fourth player name: "); //enter 4th player's name
                input = new Scanner(System.in);
                playerNameFour = input.nextLine();
                playerNames [3] = playerNameFour;

                playerNumber = (int)(Math.random() * numberOfPlayers); //randomly choose player 1, the following players are in order of entry
                playerPosition = playerNumber;
                playerTurn = playerNumber;

                buildFirstThreePlayers();

                playerFour = new Player(playerNames[playerNumber]); //build 4th player
                playerOrder[3] = playerFour.getName();
                //playerFourHand = playerFour.getHand();

                for(int i=0; i<8; ++i){ //add 8 cards to each hand and delete those cards from deck

                    fillThreeHands();
                    playerFour.getHand().add(shuffledDeck.get(0));
                    shuffledDeck.remove(0);
                }
                break;

            case 5: // create 5 player's hands

                enterFirstThreePlayersNames(); //enter first three player's names

                System.out.print("Enter fourth player name: "); //add 4th player's name
                input = new Scanner(System.in);
                playerNameFour = input.nextLine();
                playerNames [3] = playerNameFour;

                System.out.print("Enter fifth player name: "); //add 5th player's name
                input = new Scanner(System.in);
                playerNameFive = input.nextLine();
                playerNames [4] = playerNameFive;

                playerNumber = (int)(Math.random() * numberOfPlayers); //randomly choose player 1, the following players are in order of entry
                playerPosition = playerNumber;
                buildFirstThreePlayers();

                playerFour = new Player(playerNames[playerNumber]); //build 4th player
                playerOrder[3] = playerFour.getName();
                incrementPlayerNumber();
                playerFive = new Player(playerNames[playerNumber]); //build 5th player
                playerOrder[4] = playerFive.getName();

                for(int i=0; i<8; ++i){ //add 8 cards to each hand and delete those cards from deck

                    fillThreeHands();
                    playerFour.getHand().add(shuffledDeck.get(0));
                    shuffledDeck.remove(0);
                    playerFive.getHand().add(shuffledDeck.get(0));
                    shuffledDeck.remove(0);
                }
                break;
        }
    }
    public static void playerWaitToStart(){

        JOptionPane.showMessageDialog(null, playerOrder[turn] + " press OK when you are ready to play");
    }
    public static void displayPlayerSequence(){ //display the players names in order of play
        if(numberOfPlayers == 3) {
            String playerSequence = "The order of play is " + playerNames[playerPosition] + ", " + playerNames[incrementPlayerPosition()] + ", "
                    + playerNames[incrementPlayerPosition()];
            JOptionPane.showMessageDialog(null, playerSequence);
        }else if(numberOfPlayers == 4) {
            String playerSequence = "The order of play is " + playerNames[playerPosition] + ", " + playerNames[incrementPlayerPosition()] + ", "
                    + playerNames[incrementPlayerPosition()] + ", " + playerNames[incrementPlayerPosition()];
            JOptionPane.showMessageDialog(null, playerSequence);
        }else if(numberOfPlayers == 5) {
            String playerSequence = "The order of play is " + playerNames[playerPosition] + ", " + playerNames[incrementPlayerPosition()] + ", "
                    + playerNames[incrementPlayerPosition()] + ", " + playerNames[incrementPlayerPosition()] + ", " + playerNames[incrementPlayerPosition()];
            JOptionPane.showMessageDialog(null, playerSequence);
        }
    }
    public static void fillThreeHands(){

        playerOne.getHand().add(shuffledDeck.get(0));
        shuffledDeck.remove(0);
        playerTwo.getHand().add(shuffledDeck.get(0));
        shuffledDeck.remove(0);
        playerThree.getHand().add(shuffledDeck.get(0));
        shuffledDeck.remove(0);
    }
    public static void selectPlayerOneCard(){

        handCards = playerOne.getHand().size();
        int count = 1;
        StringBuilder message = new StringBuilder();
        for(int i = 0; i < handCards; ++i){

            String script = "\n" + count + ".   " + playerOne.getHand().get(i);
            message.append(script);
            ++count;
        }
        message.append("\n" + count + ". Pick a card from deck");
        message.append("\n\n" + ++count + ". Pass");
        message.append("\n\nPlease enter a number 0-" + count);
        String choice = JOptionPane.showInputDialog(null,playerOne.getName() + "'s cards are: " + message);
    }
    public static void enterFirstThreePlayersNames(){

        System.out.print("Enter first player name: ");
        Scanner input = new Scanner(System.in);
        playerNameOne = input.nextLine();
        playerNames [0] = playerNameOne;
        System.out.print("Enter second player name: ");
        input = new Scanner(System.in);
        playerNameTwo = input.nextLine();
        playerNames [1] = playerNameTwo;
        System.out.print("Enter third player name: ");
        input = new Scanner(System.in);
        playerNameThree = input.nextLine();
        playerNames [2] = playerNameThree;
    }
    public static int incrementPlayerNumber(){

        ++playerNumber;
        if(playerNumber > numberOfPlayers - 1){

            playerNumber = 0;
        }
        return playerNumber;
    }
    public static int incrementPlayerPosition(){

        ++playerPosition;
        if(playerPosition > numberOfPlayers - 1){

            playerPosition = 0;
        }
        return playerPosition;
    }
    public static void buildFirstThreePlayers(){

        playerOne = new Player(playerNames[playerNumber]);
        playerOrder[0] = playerOne.getName();
        incrementPlayerNumber();
        playerTwo = new Player(playerNames[playerNumber]);
        playerOrder[1] = playerTwo.getName();
        incrementPlayerNumber();
        playerThree = new Player(playerNames[playerNumber]);
        playerOrder[2] = playerThree.getName();
        incrementPlayerNumber();
    }
    public static void clearScreen(){

        for(int i = 0; i < 50; ++i) { //clear screen

            System.out.println("\n");
        }
    }
    public static void pressEnterToContinue(){
        System.out.print(" press ENTER to continue...");
        input = new Scanner(System.in);
        String go = input.nextLine();
    }
}
