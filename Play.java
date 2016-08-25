import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by tony on 12/08/2016.
 */

public class Play {

    static ArrayList <Card> playerOneHand;
    static ArrayList <Card> playerTwoHand;
    static ArrayList <Card> playerThreeHand;
    static ArrayList <Card> playerFourHand;
    static ArrayList <Card> playerFiveHand;
    static String [] playerNames;
    static Scanner input;
    static Player playerOne;
    static Player playerTwo;
    static Player playerThree;
    static Player playerFour;
    static Player playerFive;
    static int numberOfPlayers;
    static String playerOneName;
    static String playerTwoName;
    static String playerThreeName;
    static String playerFourName;
    static String playerFiveName;
    static int playerNumber;

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

        switch(numberOfPlayers){ //build selected number of hands of 8 cards

            case 3: //create 3 player's hands

                enterFirstThreePlayersNames(); //enter first three player's names

                playerNumber = (int)(Math.random() * numberOfPlayers); //randomly choose player 1, the following players are in order of entry

                buildFirstThreePlayers();

                for(int i=0; i<8; ++i){ //add 8 cards to each hand and remove those cards from deck

                    fillThreeHands();
                }
                //print all hands
                System.out.println("\n" + playerOne.getName() + "'s hand - Player One");
                printPlayerOneHand();
                System.out.println("\n" + playerTwo.getName() + "'s hand - Player Two");
                printPlayerTwoHand();
                System.out.println("\n" + playerThree.getName() + "'s hand - Player Three");
                printPlayerThreeHand();
                System.out.println(shuffledDeck.size());

                break;

            case 4: // create 4 player's hands

                enterFirstThreePlayersNames(); //enter first three player's names

                System.out.print("Enter fourth player name: "); //add 4th player
                input = new Scanner(System.in);
                playerFourName = input.nextLine();
                playerNames [3] = playerFourName;

                playerNumber = (int)(Math.random() * numberOfPlayers); //randomly choose player 1, the following players are in order of entry

                buildFirstThreePlayers();

                playerFour = new Player(playerNames[playerNumber]); //build 4th player
                playerFourHand = playerFour.getHand();

                for(int i=0; i<8; ++i){ //add 8 cards to each hand and delete those cards from deck

                    fillThreeHands();
                    playerFourHand.add(shuffledDeck.get(0));
                    shuffledDeck.remove(0);
                }
                //print all hands
                System.out.println("\n" + playerOne.getName() + "'s hand - Player One");
                printPlayerOneHand();
                System.out.println("\n" + playerTwo.getName() + "'s hand - Player Two");
                printPlayerTwoHand();
                System.out.println("\n" + playerThree.getName() + "'s hand - Player Three");
                printPlayerThreeHand();
                System.out.println("\n" + playerFour.getName() + "'s hand - Player Four");
                printPlayerFourHand();
                System.out.println(shuffledDeck.size());

                break;

            case 5: // create 5 player's hands

                enterFirstThreePlayersNames(); //enter first three player's names

                System.out.print("Enter fourth player name: "); //add 4th player
                input = new Scanner(System.in);
                playerFourName = input.nextLine();
                playerNames [3] = playerFourName;

                System.out.print("Enter fifth player name: "); //add 5th player
                input = new Scanner(System.in);
                playerFiveName = input.nextLine();
                playerNames [4] = playerFiveName;

                playerNumber = (int)(Math.random() * numberOfPlayers); //randomly choose player 1, the following players are in order of entry

                buildFirstThreePlayers();

                playerFour = new Player(playerNames[playerNumber]);  //build 4th player
                playerFourHand = playerFour.getHand();
                incrementPlayerNumber();
                playerFive = new Player(playerNames[playerNumber]); //build 5th player
                playerFiveHand = playerFive.getHand();

                for(int i=0; i<8; ++i){ //add 8 cards to each hand and delete those cards from deck

                    fillThreeHands();
                    playerFourHand.add(shuffledDeck.get(0));
                    shuffledDeck.remove(0);
                    playerFiveHand.add(shuffledDeck.get(0));
                    shuffledDeck.remove(0);
                }
                //print all hands
                System.out.println("\n" + playerOne.getName() + "'s hand - Player One");
                printPlayerOneHand();
                System.out.println("\n" + playerTwo.getName() + "'s hand - Player Two");
                printPlayerTwoHand();
                System.out.println("\n" + playerThree.getName() + "'s hand - Player Three");
                        printPlayerThreeHand();
                System.out.println("\n" + playerFour.getName() + "'s hand - Player Four");
                printPlayerFourHand();
                System.out.println("\n" + playerFive.getName() + "'s hand - Player Five");
                printPlayerFiveHand();
                System.out.println(shuffledDeck.size());

                break;
        }
    }
    public static void fillThreeHands(){

        playerOneHand.add(shuffledDeck.get(0));
        shuffledDeck.remove(0);
        playerTwoHand.add(shuffledDeck.get(0));
        shuffledDeck.remove(0);
        playerThreeHand.add(shuffledDeck.get(0));
        shuffledDeck.remove(0);
    }
    public static void printPlayerOneHand(){

        System.out.println("\n");

        for(int i=0; i<8; ++i) {

            if((playerOneHand.get(i)).getName().startsWith("The ")){

                System.out.println((playerOneHand.get(i)).getName() + " - Category: " +
                        ((TrumpCard)playerOneHand.get(i)).getCategory()); //print out selected Trump Card attributes
                continue;
            }
            System.out.println((playerOneHand.get(i)).getName() + " - Hardness: " +
                    ((MineralCard) playerOneHand.get(i)).getHardness()); //print out selected Mineral Card attribute
        }
    }
    public static void printPlayerTwoHand(){

        System.out.println("\n");

        for(int i=0; i<8; ++i) {

            if((playerTwoHand.get(i)).getName().startsWith("The ")){

                System.out.println((playerTwoHand.get(i)).getName() + " - Category: " +
                        ((TrumpCard)playerTwoHand.get(i)).getCategory()); //print out selected Trump Card attributes
                continue;
            }
            System.out.println((playerTwoHand.get(i)).getName() + " - Hardness: " +
                    ((MineralCard) playerTwoHand.get(i)).getHardness()); //print out selected Mineral Card attribute
        }
    }
    public static void printPlayerThreeHand(){

        System.out.println("\n");

        for(int i=0; i<8; ++i) {

            if((playerThreeHand.get(i)).getName().startsWith("The ")){

                System.out.println((playerThreeHand.get(i)).getName() + " - Category: " +
                        ((TrumpCard)playerThreeHand.get(i)).getCategory()); //print out selected Trump Card attributes
                continue;
            }
            System.out.println((playerThreeHand.get(i)).getName() + " - Hardness: " +
                    ((MineralCard) playerThreeHand.get(i)).getHardness()); //print out selected Mineral Card attribute
        }
    }
    public static void printPlayerFourHand(){

        System.out.println("\n");

        for(int i=0; i<8; ++i) {

            if((playerFourHand.get(i)).getName().startsWith("The ")){

                System.out.println((playerFourHand.get(i)).getName() + " - Category: " +
                        ((TrumpCard)playerFourHand.get(i)).getCategory()); //print out selected Trump Card attributes
                continue;
            }
            System.out.println((playerFourHand.get(i)).getName() + " - Hardness: " +
                    ((MineralCard) playerFourHand.get(i)).getHardness()); //print out selected Mineral Card attribute
        }
    }
    public static void printPlayerFiveHand(){

        System.out.println("\n");

        for(int i=0; i<8; ++i) {

            if((playerFiveHand.get(i)).getName().startsWith("The ")){

                System.out.println((playerFiveHand.get(i)).getName() + " - Category: " +
                        ((TrumpCard)playerFiveHand.get(i)).getCategory()); //print out selected Trump Card attributes
                continue;
            }
            System.out.println((playerFiveHand.get(i)).getName() + " - Hardness: " +
                    ((MineralCard) playerFiveHand.get(i)).getHardness()); //print out selected Mineral Card attribute
        }
    }
    public static void enterFirstThreePlayersNames(){

        System.out.print("Enter first player name: ");
        Scanner input = new Scanner(System.in);
        playerOneName = input.nextLine();
        playerNames [0] = playerOneName;
        System.out.print("Enter second player name: ");
        input = new Scanner(System.in);
        playerTwoName = input.nextLine();
        playerNames [1] = playerTwoName;
        System.out.print("Enter third player name: ");
        input = new Scanner(System.in);
        playerThreeName = input.nextLine();
        playerNames [2] = playerThreeName;
    }
    public static int incrementPlayerNumber(){

        ++playerNumber;
        if(playerNumber > numberOfPlayers - 1){

            playerNumber = 0;
        }
        return playerNumber;
    }
    public static void buildFirstThreePlayers(){

        playerOne = new Player(playerNames[playerNumber]);
        playerOneHand = playerOne.getHand();
        incrementPlayerNumber();
        playerTwo = new Player(playerNames[playerNumber]);
        playerTwoHand = playerTwo.getHand();
        incrementPlayerNumber();
        playerThree = new Player(playerNames[playerNumber]);
        playerThreeHand = playerThree.getHand();
        incrementPlayerNumber();
    }
}
