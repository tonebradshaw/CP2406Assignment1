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
    static String playerNameOne;
    static String playerNameTwo;
    static String playerNameThree;
    static String playerNameFour;
    static String playerNameFive;
    static int playerNumber, numberOfPlayers;
    static int playerPosition, gos, compare;
    static int handCards, category, question, card;
    static String choice, activeCategory;
    static String activeCardNotice;

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

        //input - enter how many players
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

        gos = 0;

        switch(numberOfPlayers){ //build selected number of hands of 8 cards

            case 3: //create 3 player's hands

                enterFirstThreePlayersNames(); //enter first three player's names

                playerNumber = (int)(Math.random() * numberOfPlayers); //randomly choose player 1, the following players are in order of entry
                playerPosition = playerNumber; //allow playerNumber to be used in different sequences

                buildFirstThreePlayers(); //build playerOne, playerTwo and playerThree

                for(int i=0; i<8; ++i){ //add 8 cards to each hand and remove those cards from deck

                    fillThreeHands();
                }
                if(gos == 0) { //first player play

                    displayPlayerSequence();
                    playerWaitToStart();

                    do { //view cards and select active category until satisfied

                        category = selectFirstCategory(players[playerNumber]); //display cards and select category
                        question = checkCategory(category); //check selected category

                    }while(question != 0); //until category is accepted

                    activeCategory = categories[category];
                    card = selectFirstCard(); //select card to throw
                    discardedCards.add(0, hand.get(card)); //add chosen card to discard pile
                    hand.remove(card); //remove card from hand
                    activeCard = discardedCards.get(0); //select active card
                    activeCardNotice = getActiveCardValues(); //add to display to show the active category and value
                    incrementPlayerPosition();
                    gos = 1;
                }
                do{
                    compare = 0; //set compare to false
                    incrementPlayerNumber(); //next player
                    playerWaitToStart(); //wait til player is ready

                    do { //get card and check whether selected card category value > active card value

                        card = selectPlayerCard(players[playerNumber]); //return list selection

                        if (card == players[playerNumber].getHand().size()) { //pick up selected

                            if(shuffledDeck.size() == 0){ //if deck has been used

                                JOptionPane.showMessageDialog(null, "There are no more cards to pick up " +
                                        "\nYou must choose \"Pass\"");
                                compare = 0;
                            }else{
                                players[playerNumber].getHand().add(shuffledDeck.get(0)); //get another card
                                shuffledDeck.remove(0); //remove card from deck
                                System.out.println(shuffledDeck.size());
                                compare = 1; //allow
                            }

                        }else if(card == players[playerNumber].getHand().size() + 1){

                            compare = 1;
                        }else{

                            compare = compareValues(hand.get(card), activeCategory); //compare card selected to active card
                            if(compare == 1){
                                discardedCards.add(0, hand.get(card)); //add chosen card to discard pile
                                hand.remove(card); //remove card from hand
                            }
                        }
                    }while(compare == 0);

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
    public static void playerWaitToStart(){ //wait for player so cards are not visible to other players

        JOptionPane.showMessageDialog(null, players[playerPosition].getName() + " press OK when you are ready to play");
    }
    public static void displayPlayerSequence(){ //display the players names in order of play

        if(numberOfPlayers == 3) {
            String playerSequence = playerNumber + " The order of play is " + playerNames[playerPosition] + ", " + playerNames[incrementPlayerPosition()] + ", "
                    + playerNames[incrementPlayerPosition()];
            JOptionPane.showMessageDialog(null, playerSequence);
            System.out.println(playerNumber + " 1");
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
    }
    public static void fillThreeHands(){ //add cards to first three players hands

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

        for(int i = 0; i < handCards; ++i){ //build player selection display

            String script = "\n" + count + ".   " + player.getHand().get(i);
            message.append(script);
            ++count;
        }
        message.append("\n");
        message.append(count);
        message.append(". Pick a card from deck");
        message.append("\n\n");
        message.append(++count);
        message.append(". Pass");
        message.append("\n\n");
        message.append("Please enter a number 1-");
        message.append(count);

        do { //continue to choose the category number while selection is not among the choices
            try{
                choice = JOptionPane.showInputDialog(null,activeCardNotice + "\n" + players[playerPosition].getName() + "'s cards are: " + message);
                number = Integer.parseInt(choice);

            } catch (Exception e) { //catch an input that isn't an integer

                JOptionPane.showMessageDialog(null, "You must enter an integer 1-" + handCards + 2);
            }
        }while(number < 1 || number > handCards + 2);
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
        message.append("\nChoose a category (1-5): \n"); //add question
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
                choice = JOptionPane.showInputDialog(null, players[playerNumber].getName() + "'s cards are: " + message);
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

        message.append("\nThe Active category is ");
        message.append(activeCategory);
        message.append(".\n"); //print active card and chosen active category
        message.append(players[playerNumber].getName() + "'s cards are: ");

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
    public static String getActiveCardValues(){ //build message at top of screen

        stringBuilder = new StringBuilder();

        if(activeCard.getName().startsWith("The ")){ //check if active card is a trump card
            stringBuilder.append("Active Card is: "); //add active card name
            stringBuilder.append(activeCard.getName());
            if(!((TrumpCard)activeCard).getCategory().equals("all")){ //if active card category is not "all"

                activeCategory = ((TrumpCard)activeCard).getCategory(); //select trump card category as active category
            }
            if(activeCard.getName().equals("The Geophysicist")){ //if trump card is The Geophysicist
                stringBuilder.append("\nYou may also throw the \"Magnetite\" card");
                activeCategory = "Specific Gravity"; //select specific gravity as the active category
            } else if(activeCard.getName().equals("The Geologist")){ //if active card category is "all", player needs to choose category
                do { //view cards and select active category until satisfied

                    category = selectFirstCategory(players[playerPosition]); //display cards and select category
                    question = checkCategory(category); //check selected category

                }while(question != 0); //until category is accepted

                activeCategory = categories[category];
            }
            stringBuilder.append("\nThe Active category is ");
            stringBuilder.append(activeCategory);
            stringBuilder.append("\n");

            return stringBuilder.toString();
        }else { //message if active card is a mineral card
            stringBuilder.append("Active Card is: ");
            stringBuilder.append(activeCard.getName());
            stringBuilder.append( ". ");
            stringBuilder.append("\nThe Active category is ");
            stringBuilder.append(activeCategory);
            stringBuilder.append(".\n"); //print active card and chosen active category

            //select message for different category
            if(activeCategory.equals("Hardness")){
                stringBuilder.append("Your ");
                stringBuilder.append(activeCategory);
                stringBuilder.append(" value needs to be > ");
                stringBuilder.append(((MineralCard) activeCard).getHardness());
            }else if(activeCategory.equals("Specific Gravity")){
                stringBuilder.append("Your ");
                stringBuilder.append(activeCategory);
                stringBuilder.append(" value needs to be > ");
                stringBuilder.append(((MineralCard) activeCard).getSpecificGravity());
            }else if(activeCategory.equals("Cleavage")){
                stringBuilder.append("Your ");
                stringBuilder.append(activeCategory);
                stringBuilder.append(" value needs to be > ");
                stringBuilder.append(((MineralCard)activeCard).getCleavage());
            }else if(activeCategory.equals("Crustal Abundance")){
                stringBuilder.append("Your ");
                stringBuilder.append(activeCategory);
                stringBuilder.append(" value needs to be > ");
                stringBuilder.append(((MineralCard)activeCard).getCrustalAbundance());
            }else if(activeCategory.equals("Economic Value")){
                stringBuilder.append("Your ");
                stringBuilder.append(activeCategory);
                stringBuilder.append(" value needs to be > ");
                stringBuilder.append(((MineralCard)activeCard).getEconomicValue());
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }
    public static int compareValues(Card card, String checkCategory){ //compare active card and chosen card

        if(activeCard.getName().startsWith("The ") || card.getName().startsWith("The ")){ //skip trump cards

            compare = 1;

        }else if(checkCategory.equals("Hardness")){ //compare hardness values

            if(((MineralCard)card).getHardness() > ((MineralCard)activeCard).getHardness()){

                compare = 1;
            }
        }else if(activeCategory.equals("Specific Gravity")) { //compare specific gravity values or pass Magnetite card

            if (((MineralCard) card).getSpecificGravity() > ((MineralCard) activeCard).getSpecificGravity() || card.getName().equals("Magnetite")) {

                compare = 1;
            }
        }else if(activeCategory.equals("Cleavage")) { //compare cleavage values

            int index1 = Arrays.asList(cleavageHierarchy).indexOf(((MineralCard) card).getCleavage()); //get index number of played card in array
            int index2 = Arrays.asList(cleavageHierarchy).indexOf(((MineralCard) activeCard).getCleavage()); //get index number of active card in array

            if (index1 > index2){ // if card cleavage is higher than active card

                compare = 1;
            }
        }else if(activeCategory.equals("Crustal Abundance")) { //compare crustal abundance values

            int index1 = Arrays.asList(crustalAbundanceHierarchy).indexOf(((MineralCard) card).getCrustalAbundance()); //get index number of played card in array
            int index2 = Arrays.asList(crustalAbundanceHierarchy).indexOf(((MineralCard) activeCard).getCrustalAbundance()); //get index number of active card in array

            if (index1 > index2){ // // if card crustal abundance is higher than active card

                compare = 1;
            }
        }else if(activeCategory.equals("Economic Value")) { //compare economic value values

            int index1 = Arrays.asList(economicValueHierarchy).indexOf(((MineralCard) card).getEconomicValue()); //get index number of played card in array
            int index2 = Arrays.asList(economicValueHierarchy).indexOf(((MineralCard) activeCard).getEconomicValue()); //get index number of active card in array

            if (index1 > index2){ // // if card economic value is higher than active card

                compare = 1;
            }
        }
        return compare;
    }
}
