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
    static ArrayList <Card> hand;
    static ArrayList <Card> discardedCards;
    static Card activeCard;
    static Scanner input;
    static StringBuilder stringBuilder;
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
    static int playerPosition, gos;
    static double value;
    static int handCards, category, question, card;
    static String entry, choice, activeCategory, message1;
    static String playerSequence, activeCardNotice;

    static ArrayList <Card> shuffledDeck = new ArrayList<>();
    static Player [] players = new Player [5];
    static String [] cleavageHierarchy = {"none", "poor/none", "1 poor", "2 poor", "1 good", "1 good, 1 poor", "2 good", "3 good",
            "1 perfect", "1 perfect, 1 good", "1 perfect, 2 good", "2 perfect, 1 good", "3 perfect", "4 perfect", "6 perfect"};
    static String [] crustalAbundanceHierarchy = {"ultratrace", "trace", "low", "moderate", "high", "very high"};
    static String [] economicValueHierarchy = {"trivial", "low", "moderate", "high", "very high", "I'm rich!"};
    static String [] categories = {"Hardness", "Specific Gravity", "Cleavage", "Crustal Abundance", "Economic Value"};

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

        }while(numberOfPlayers != 3 && numberOfPlayers != 4 && numberOfPlayers != 5); //loop while number of players is out of range

        playerNames = new String [numberOfPlayers]; //array containing player's names
        playerOrder = new String [numberOfPlayers]; //array containing player's names in order of play
        discardedCards = new ArrayList<>(); //arrayList containing the discard pile

        turn = 0;
        gos = 0;

        switch(numberOfPlayers){ //build selected number of hands of 8 cards

            case 3: //create 3 player's hands

                enterFirstThreePlayersNames(); //enter first three player's names

                playerNumber = (int)(Math.random() * numberOfPlayers); //randomly choose player 1, the following players are in order of entry
                playerPosition = playerNumber; //allow playerNumber to be used in different sequences
                System.out.println(playerPosition + " 1");
                buildFirstThreePlayers(); //build playerOne, playerTwo and playerThree
                System.out.println(playerPosition + " 2");
                for(int i=0; i<8; ++i){ //add 8 cards to each hand and remove those cards from deck

                    fillThreeHands();
                }
                System.out.println(playerPosition + " 3");
                if(gos == 0) { //first person play
                    displayPlayerSequence();
                    System.out.println(playerPosition + " 4");
                    playerWaitToStart();
                    System.out.println(playerPosition + " 5");
                    do { //view cards and select active category until satisfied

                        category = selectFirstCategory(players[playerPosition]); //display cards and select category
                        System.out.println(playerPosition + " 6");
                        question = checkCategory(category); //check selected category
                        System.out.println(playerPosition + " 7");

                    }while(question != 0); //until category is accepted
                    System.out.println(playerPosition + " 8");

                    activeCategory = categories[category];

                    card = selectFirstCard(); //select card to throw
                    System.out.println(playerPosition + " 9");
                    discardedCards.add(0, hand.get(card)); //add chosen card to discard pile
                    System.out.println(playerPosition + " 10");
                    hand.remove(card); //remove card from hand

                    activeCard = discardedCards.get(0); //select active card

                    activeCardNotice = getActiveCardValues(); //add to display to show the active category and value
                    incrementPlayerPosition();
                    System.out.println(playerPosition + " 11");
                    gos = 1;
                }
                do{
                    System.out.println(playerPosition + " 12");
                    playerWaitToStart();
                    card = selectPlayerCard(players[playerPosition]);
                    discardedCards.add(0, hand.get(card)); //add chosen card to discard pile
                    hand.remove(card); //remove card from hand
                    activeCard = discardedCards.get(0); //select active card
                    activeCardNotice = getActiveCardValues(); //add to display to show the active category and value
                    incrementPlayerPosition();

                }while(playerOne.getHand().size() > 0 && playerTwo.getHand().size() > 0 && playerThree.getHand().size() > 0);

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
                players[3] = playerFour;
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
                players[5] = playerFive;
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

        JOptionPane.showMessageDialog(null, playerOrder[playerPosition] + " press OK when you are ready to play");
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
        incrementPlayerPosition();
        incrementPlayerPosition();
    }
    public static void fillThreeHands(){

        playerOne.getHand().add(shuffledDeck.get(0));
        shuffledDeck.remove(0);
        playerTwo.getHand().add(shuffledDeck.get(0));
        shuffledDeck.remove(0);
        playerThree.getHand().add(shuffledDeck.get(0));
        shuffledDeck.remove(0);
    }
    public static int selectPlayerCard(Player player){ //select card from selected hand

        hand = player.getHand();
        handCards = player.getHand().size();
        int count = 1;
        int number = 0;
        StringBuilder message = new StringBuilder();

        for(int i = 0; i < handCards; ++i){

            String script = "\n" + count + ".   " + player.getHand().get(i);
            message.append(script);
            ++count;
        }
        message.append("\n" + count + ". Pick a card from deck");
        message.append("\n\n" + ++count + ". Pass");
        message.append("\n\nPlease enter a number 1-" + count);

        do { //continue to choose the category number while selection is not among the choices
            try{
                choice = JOptionPane.showInputDialog(null,activeCardNotice + "\n" + players[playerPosition].getName() + "'s cards are: " + message);
                number = Integer.parseInt(choice);

            } catch (Exception e) { //catch an input that isn't an integer

                JOptionPane.showMessageDialog(null, "You must enter an integer 1-" + handCards);
            }

        }while(number < 1 || number > handCards);
        return number - 1; //change value to element number
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
    public static void buildFirstThreePlayers(){ //build three players using the names in order of play; add names to playerOrder array

        playerOne = new Player(playerNames[playerNumber]);
        players[0] = playerOne;
        playerOrder[0] = playerOne.getName();
        incrementPlayerNumber();
        playerTwo = new Player(playerNames[playerNumber]);
        players[1] = playerTwo;
        playerOrder[1] = playerTwo.getName();
        incrementPlayerNumber();
        playerThree = new Player(playerNames[playerNumber]);
        players[2] = playerThree;
        playerOrder[2] = playerThree.getName();
        incrementPlayerNumber();
    }
    public static int selectFirstCategory(Player player){ //view hand and select category

        int number = 0;
        hand = player.getHand();
        handCards = player.getHand().size();
        StringBuilder message = new StringBuilder();

        for(int i = 0; i < handCards; ++i){ //append each card to hand to display

            String script = "\n" + hand.get(i);
            message.append(script);
        }
        message.append("\nChoose a category (1-5): \n");
        int count = 1;

        for(int i = 0; i < categories.length; ++i){ //append each number selection with category

            message.append(count);
            message.append("  ");
            message.append(categories[i]);
            message.append("  ");
            ++count;
        }
        do { //continue to choose the category number while selection is not among the choices
            try{
                choice = JOptionPane.showInputDialog(null, message);
                number = Integer.parseInt(choice);

            } catch (Exception e) { //catch an input that isn't an integer

                JOptionPane.showMessageDialog(null, "You must enter an integer 1-5");
            }
            if(number < 0 || number > categories.length){

                JOptionPane.showMessageDialog(null, "You must enter an integer 1-" + categories.length);
            }
        }while(number < 1 || number > categories.length);
        return number - 1; //change value to element number
    }
    static public int checkCategory(int category){ //check if selected category is wanted

        return JOptionPane.showConfirmDialog(null, "You have chosen " + categories[category] +
                "\nIs this correct?", "You have chosen " + categories[category] +
                "\nIs this correct?", JOptionPane.YES_NO_OPTION);
    }
    public static int selectFirstCard(){ //select first card to throw

        int count = 1;
        int number = 0;
        handCards = hand.size();
        StringBuilder message = new StringBuilder();

        for(int i = 0; i < handCards; ++i){ //display hand with selection numbers

            String script = "\n" + count + " - " + hand.get(i);
            message.append(script);
            ++count;
        }
        message.append("\nChoose a card 1-");
        message.append(hand.size());

        do { //continue until an acceptable card is chosen
            try{
                choice = JOptionPane.showInputDialog(null, message);
                number = Integer.parseInt(choice);

            } catch (Exception e) { //catch an input that isn't an integer

                JOptionPane.showMessageDialog(null, "You must enter an integer 1-" + hand.size());
            }
            if(number < 0 || number > hand.size()){

                JOptionPane.showMessageDialog(null, "You must enter an integer 1-" + hand.size());
            }
        }while(number < 1 || number > hand.size() + 2);
        return number - 1; //change to element number
    }
    public static String getActiveCardValues(){

        stringBuilder = new StringBuilder();

        if(activeCard.getName().startsWith("The ")){
            stringBuilder.append("Active Card is: " + activeCard.getName() +
                    "\nthe Active category is " + ((TrumpCard)activeCard).getCategory() + "\n");
            activeCategory = ((TrumpCard)activeCard).getCategory();
            return stringBuilder.toString();
        }else {
            stringBuilder.append("Active Card is: " + activeCard.getName() + ". " +
                    "\nthe Active category is " + activeCategory + ".\n"); //print active card and chosen active category

            if(activeCategory.equals("Hardness")){
                stringBuilder.append("Your " + activeCategory + " value needs to be > " + ((MineralCard) activeCard).getHardness());
            }else if(activeCategory.equals("Specific Gravity")){
                stringBuilder.append("Your " + activeCategory + " value needs to be > " + ((MineralCard) activeCard).getSpecificGravity());
            }else if(activeCategory.equals("Cleavage")){
                stringBuilder.append("Your " + activeCategory + " value needs to be > " + ((MineralCard)activeCard).getCleavage());
            }else if(activeCategory.equals("Crustal Abundance")){
                stringBuilder.append("Your " + activeCategory + " value needs to be > " + ((MineralCard)activeCard).getCrustalAbundance());
            }else if(activeCategory.equals("Economic Value")){
                stringBuilder.append("Your " + activeCategory + " value needs to be > " + ((MineralCard)activeCard).getEconomicValue());
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
    public static int incrementTurn(){

        ++turn;
        if(turn > numberOfPlayers - 1){
            turn = 0;
        }
        return turn;
    }
}
