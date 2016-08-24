import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

/**
 * Created by tony on 12/08/2016.
 */

public class Play {

    static ArrayList <ArrayList> hands;
    static ArrayList <Card> playerOneHand;
    static ArrayList <Card> playerTwoHand;
    static ArrayList <Card> playerThreeHand;
    static ArrayList <Card> playerFourHand;
    static ArrayList <Card> playerFiveHand;
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

        Collections.shuffle(shuffledDeck);

        for(int i=0; i<60; ++i) { //print each card with attributes from shuffled Deck
            System.out.println(shuffledDeck.get(i));
        }

        //input - select how many players
        do { //check input until an integer between 3 and 5 is entered
            try {
                System.out.print("Enter number of players (3-5): ");
                Scanner input = new Scanner(System.in);
                numberOfPlayers = input.nextInt();

            } catch (Exception e) {
                System.out.print("You must enter an Integer - ");
            }

        }while(numberOfPlayers != 3 && numberOfPlayers != 4 && numberOfPlayers != 5);

        playerOneHand = new ArrayList<>();
        playerTwoHand = new ArrayList<>();
        playerThreeHand = new ArrayList<>();

        switch(numberOfPlayers){ //build selected number of hands of 8 cards

            case 3: // create 3 player's hands
                //Hand hands = new Hand(shuffledDeck, playerOneHand, playerTwoHand, playerThreeHand);

                enterFirstThreePlayersNames();



                for(int i=0; i<8; ++i){ //add 8 cards to each hand and remove those cards from deck

                    fillThreeHands();
                }
                System.out.println("\n" + playerOne.getName() + "'s hand - Player One");
                printPlayerOneHand();
                System.out.println("\n" + playerTwo.getName() + "'s hand - Player Two");
                printPlayerTwoHand();
                System.out.println("\n" + playerThree.getName() + "'s hand - Player Three");
                printPlayerThreeHand();
                System.out.println(shuffledDeck.size());

                break;

            case 4: // create 4 player's hands

                playerFourHand = new ArrayList<>();

                //Hand hands = new Hand(shuffledDeck, playerOneHand, playerTwoHand, playerThreeHand, playerFourHand);

                enterFirstThreePlayersNames();

                System.out.print("Enter fourth player name: ");
                input = new Scanner(System.in);
                playerFourName = input.nextLine();
                playerFour = new Player(playerFourName);

                for(int i=0; i<8; ++i){ //add 8 cards to each hand and delete those cards from deck

                    fillThreeHands();
                    playerFourHand.add(shuffledDeck.get(0));
                    shuffledDeck.remove(0);
                }
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

                playerFourHand = new ArrayList<>();
                playerFiveHand = new ArrayList<>();

                //Hand hands = new Hand(shuffledDeck, playerOneHand, playerTwoHand, playerThreeHand, playerFourHand, playerFiveHand);

                enterFirstThreePlayersNames();

                System.out.print("Enter fourth player name: ");
                input = new Scanner(System.in);
                playerFourName = input.nextLine();
                playerFour = new Player(playerFourName);

                System.out.print("Enter fifth player name: ");
                input = new Scanner(System.in);
                playerFiveName = input.nextLine();
                playerFive = new Player(playerFiveName);

                for(int i=0; i<8; ++i){ //add 8 cards to each hand and delete those cards from deck

                    fillThreeHands();
                    playerFourHand.add(shuffledDeck.get(0));
                    shuffledDeck.remove(0);
                    playerFiveHand.add(shuffledDeck.get(0));
                    shuffledDeck.remove(0);
                }
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

        printCardAttributesFromShuffledDeck(); //print name and selected attribute of Mineral and Trump Cards in shuffledDeck
        checkCleavageValue(); //check which of 2 mineral cards has the higher cleavage
        checkCrustalAbundanceValue(); //check which of 2 mineral cards has the higher crustal abundance
        checkEconomicValue(); //check which of 2 mineral cards has the higher economic value

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
    public static void printCardAttributesFromShuffledDeck(){ //print name and selected attribute of all Mineral and Trump Cards in shuffledDeck arrayList

        for(int i=0; i<shuffledDeck.size(); ++i) {

            if((shuffledDeck.get(i)).getName().startsWith("The ")){

                System.out.println((shuffledDeck.get(i)).getName() + " - Category: " +
                        ((TrumpCard)shuffledDeck.get(i)).getCategory()); //print out selected Trump Card attributes
                continue;
            }
            System.out.println((shuffledDeck.get(i)).getName() + " - Hardness: " +
                    ((MineralCard) shuffledDeck.get(i)).getHardness()); //print out selected Mineral Card attribute
        }
    }
    public static void checkCleavageValue(){ //check which of 2 mineral cards has the higher cleavage

        int number = getNumber(); //select the first card position number


        int index1 = Arrays.asList(cleavageHierarchy).indexOf(((MineralCard) shuffledDeck.get(number)).getCleavage()); //get cleavage value of first card
        int index2 = Arrays.asList(cleavageHierarchy).indexOf(((MineralCard) shuffledDeck.get(number + 1)).getCleavage()); //get cleavage value of second card

        if (index1 == index2){ // if cleavage values are equal

            System.out.println("\n" + number + " " + (shuffledDeck.get(number)).getName() + " has the same value \"" +
                    ((MineralCard) shuffledDeck.get(number)).getCleavage() + "\" of cleavage as " + (shuffledDeck.get(number + 1)).getName() +
                    " whose value is \"" + ((MineralCard) shuffledDeck.get(number +1)).getCleavage() + "\"");

        } else if (index1 > index2){ //if cleavage value of first card > than second card

            System.out.println("\n" + number + " " + (shuffledDeck.get(number)).getName() + " has a greater value \"" +
                    ((MineralCard) shuffledDeck.get(number)).getCleavage() + "\" of cleavage than " + (shuffledDeck.get(number + 1)).getName() +
                    " whose value is \"" + ((MineralCard) shuffledDeck.get(number + 1)).getCleavage() + "\"");

        } else { //if cleavage value of second card > than first card

            System.out.println("\n" + number + " " + (shuffledDeck.get(number + 1)).getName() + " has a greater value \"" +
                    ((MineralCard) shuffledDeck.get( number + 1)).getCleavage() + "\" of cleavage than " + (shuffledDeck.get(number)).getName() +
                    " whose value is \"" + ((MineralCard) shuffledDeck.get(number)).getCleavage() + "\"");
        }
    }
    public static void checkCrustalAbundanceValue(){ //check which of 2 mineral cards has the higher crustal abundance

        int number = getNumber(); //select the first card position number

        int index1 = Arrays.asList(crustalAbundanceHierarchy).indexOf(((MineralCard) shuffledDeck.get(number)).getCrustalAbundance());
        int index2 = Arrays.asList(crustalAbundanceHierarchy).indexOf(((MineralCard) shuffledDeck.get(number + 1)).getCrustalAbundance());

        if (index1 == index2){ // if crustal abundance values are equal

            System.out.println((shuffledDeck.get(number)).getName() + " has the same value \"" +
                    ((MineralCard) shuffledDeck.get(number)).getCrustalAbundance() + "\" of Crustal Abundance as " + (shuffledDeck.get(number + 1)).getName() +
                    " whose value is \"" + ((MineralCard) shuffledDeck.get(number + 1)).getCrustalAbundance() + "\"");

        } else if (index1 > index2){ //if crustal abundance value of first card > than second card

            System.out.println((shuffledDeck.get(number)).getName() + " has a greater value \"" +
                    ((MineralCard) shuffledDeck.get(number)).getCrustalAbundance() + "\" of Crustal Abundance than " + (shuffledDeck.get(number + 1)).getName() +
                    " whose value is \"" + ((MineralCard) shuffledDeck.get(number + 1)).getCrustalAbundance() + "\"");

        } else { //if crustal abundance value of second card > than first card

            System.out.println((shuffledDeck.get(number + 1)).getName() + " has a greater value \"" +
                    ((MineralCard) shuffledDeck.get(number + 1)).getCrustalAbundance() + "\" of Crustal Abundance than " + (shuffledDeck.get(number)).getName() +
                    " whose value is \"" + ((MineralCard) shuffledDeck.get(number)).getCrustalAbundance() + "\"");
        }
    }
    public static void checkEconomicValue(){ //check which of 2 mineral cards has the higher economic value

        int number = getNumber(); //select the first card position number

        int index1 = Arrays.asList(economicValueHierarchy).indexOf(((MineralCard) shuffledDeck.get(number)).getEconomicValue());
        int index2 = Arrays.asList(economicValueHierarchy).indexOf(((MineralCard) shuffledDeck.get(number + 1)).getEconomicValue());

        if (index1 == index2){ // if economic values are equal

            System.out.println((shuffledDeck.get(number)).getName() + " has the same value \"" +
                    ((MineralCard) shuffledDeck.get(number)).getEconomicValue() + "\" of Economic Value as " + (shuffledDeck.get(number + 1)).getName() +
                    " whose value is \"" + ((MineralCard) shuffledDeck.get(number + 1)).getEconomicValue() + "\"" + "\n");

        } else if (index1 > index2){ //if economic value of first card > than second card

            System.out.println((shuffledDeck.get(number)).getName() + " has a greater value \"" +
                    ((MineralCard) shuffledDeck.get(number)).getEconomicValue() + "\" of Economic Value than " + (shuffledDeck.get(number + 1)).getName() +
                    " whose value is \"" + ((MineralCard) shuffledDeck.get(number + 1)).getEconomicValue() + "\"" + "\n");

        } else { //if economic value of second card > than first card

            System.out.println((shuffledDeck.get(number + 1)).getName() + " has a greater value \"" +
                    ((MineralCard) shuffledDeck.get(number + 1)).getEconomicValue() + "\" of Economic Value than " + (shuffledDeck.get(number)).getName() +
                    " whose value is \"" + ((MineralCard) shuffledDeck.get(number)).getEconomicValue() + "\"" + "\n");
        }
    }
    public static int getNumber(){ //select 2 adjacent mineral cards in the deck, skip trump cards

        int number = 1;

        while((shuffledDeck.get(number)).getName().startsWith("The ") ||
                (shuffledDeck.get(number + 1)).getName().startsWith("The ")){ //make sure there are no trump cards

            ++number;
        }
        return number;
    }
    public static void enterFirstThreePlayersNames(){

        System.out.print("Enter first player name: ");
        Scanner input = new Scanner(System.in);
        playerOneName = input.nextLine();
        playerOne = new Player(playerOneName);
        System.out.print("Enter second player name: ");
        input = new Scanner(System.in);
        playerTwoName = input.nextLine();
        playerTwo = new Player(playerTwoName);
        System.out.print("Enter third player name: ");
        input = new Scanner(System.in);
        playerThreeName = input.nextLine();
        playerThree = new Player(playerThreeName);
    }
}
