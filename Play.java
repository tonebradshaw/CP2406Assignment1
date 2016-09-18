import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class Play {

    static ArrayList <Card> hand;
    static ArrayList <Card> discardedCards;
    static ArrayList <Card> shuffledDeck = new ArrayList<>();

    static Card activeCard;
    static Scanner input;
    static StringBuilder stringBuilder;
    static Player playerOne, playerTwo, playerThree, playerFour, playerFive;

    static int playerNumber, numberOfPlayers, gos, compare, playGame, handCards, category, question, card, holdPlayerNumber;
    static String playerNameOne, playerNameTwo, playerNameThree, playerNameFour, playerNameFive;
    static String choice, activeCategory, activeCardNotice, menu;

    static final int NUMBER_OF_CARDS_PER_HAND = 8;

    static String [] playerNames;
    static Card [] hold;
    static Player [] players;

    static String [] cleavageHierarchy = {"none", "poor/none", "1 poor", "2 poor", "1 good", "1 good, 1 poor", "2 good", "3 good",
            "1 perfect", "1 perfect, 1 good", "1 perfect, 2 good", "2 perfect, 1 good", "3 perfect", "4 perfect", "6 perfect"};
    static String [] crustalAbundanceHierarchy = {"ultratrace", "trace", "low", "moderate", "high", "very high"};
    static String [] economicValueHierarchy = {"trivial", "low", "moderate", "high", "very high", "I'm rich!"};
    static String [] categories = {"Hardness", "Specific Gravity", "Cleavage", "Crustal Abundance", "Economic Value"};

    public static void main(String[] args) {

        playGame = playGameMenu(); //show rules and play or quit option

        if(playGame == 2){

            System.exit(0); //end program
        }
        Deck deck = new Deck();

        for (int i = 0; i < Deck.DECK_SIZE; ++i) { //convert Deck array to shuffledDeck ArrayList

            shuffledDeck.add(deck.cards[i]);
        }
        Collections.shuffle(shuffledDeck); //shuffle the deck

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
        discardedCards = new ArrayList<>(); //arrayList containing the discard pile
        players = new Player[numberOfPlayers]; //array containing Player objects

        gos = 0; //first player cycle

        switch(numberOfPlayers){ //build selected number of hands of 8 cards

            case 3: //create 3 player's hands

                enterFirstThreePlayersNames(); //enter first three player's names

                playerNumber = (int)(Math.random() * numberOfPlayers); //randomly choose player 1, the following players are in order of entry

                buildFirstThreePlayers(); //build playerOne, playerTwo and playerThree

                for(int i=0; i < NUMBER_OF_CARDS_PER_HAND; ++i){ //add 8 cards to each hand and remove those cards from deck

                    fillThreeHands();
                }
                if(gos == 0) { //first player plays

                    firstPlayOfGame();
                }
                do{
                    compare = 0; //set compare to false
                    incrementPlayerNumber(); //next player
                    playerWaitToStart(); //wait til player is ready
                    holdPlayerNumber = playerNumber; //hold playerNumber to reset after cycle

                    if(players[playerNumber].getPickUpCard() == 1 && (players[incrementPlayerNumber()].getPickUpCard() +
                            players[incrementPlayerNumber()].getPickUpCard()) == 0){ //if player has picked up and none of the remaining 2 players
                                                                                        //have picked up in the last round then player must pass

                        playerNumber = holdPlayerNumber; //reset playerNumber after check for pass conditions
                        JOptionPane.showMessageDialog(null, players[playerNumber].getName() + ", you have picked up from the deck recently" +
                                "\nMore then 1 player has discarded per round since you picked up" +
                                "\nNo Trump Card has been thrown since you picked up" +
                                "\nYou therefore must pass this turn");
                    }else{

                        playerNumber = holdPlayerNumber; //reset playerNumber after check for pass conditions

                        do { //get card and check whether selected card category value > active card value

                            card = selectPlayerCard(players[playerNumber]); //return list selection

                            if (card == players[playerNumber].getHand().size()) { //pick up from deck

                                players[playerNumber].setPickUpCard(1); //card picked up

                                if (shuffledDeck.size() == 0) { //if there are no more cards to pick up

                                    hold = new Card[1]; //create holding array
                                    hold[0] = discardedCards.get(0); //add active card to array
                                    discardedCards.remove(0); //remove active card from discard pile

                                    for (int i = discardedCards.size() - 1; i >= 0; --i) { //transfer discards to shuffled deck

                                        shuffledDeck.add(discardedCards.get(i)); //add card to deck
                                        discardedCards.remove(i); //remove card from pile
                                    }
                                    Collections.shuffle(shuffledDeck); //shuffle the deck
                                    discardedCards.add(hold[0]); //add active card to empty discard pile
                                }
                                players[playerNumber].getHand().add(shuffledDeck.get(0)); //get another card
                                shuffledDeck.remove(0); //remove card from deck
                                compare = 1; //allow

                            } else {
                                compare = compareValues(hand.get(card)); //compare card selected to active card

                                if (compare == 1) {
                                    players[playerNumber].setPickUpCard(0); //set value to card thrown
                                    discardedCards.add(0, hand.get(card)); //add chosen card to discard pile
                                    hand.remove(card); //remove card from hand
                                }
                            }
                        } while (compare == 0); //loop until compare != 0
                        activeCard = discardedCards.get(0); //select active card after throw

                        if(activeCard.getName().startsWith("The ")){ //if trump card thrown, reset all pickUpCard values

                            holdPlayerNumber = playerNumber; //hold playerNumber to reset after cycle
                            players[playerNumber].setPickUpCard(0);
                            players[incrementPlayerNumber()].setPickUpCard(0);
                            players[incrementPlayerNumber()].setPickUpCard(0);
                            playerNumber = holdPlayerNumber; //reset playerNumber
                        }
                        activeCardNotice = getActiveCardValues(); //add to display to show the active category and value
                    }

                }while(playerOne.getHand().size() > 0 && playerTwo.getHand().size() > 0 && playerThree.getHand().size() > 0); //loop until end of game
                JOptionPane.showMessageDialog(null, "Game Over");
                //display winner
                if(playerOne.getHand().size() == 0){
                    JOptionPane.showMessageDialog(null, "Yay!! Congratz on the win " + playerOne.getName());
                }else if(playerTwo.getHand().size() == 0){
                    JOptionPane.showMessageDialog(null, "Yay!! Congratz on the win " + playerTwo.getName());
                }else if(playerThree.getHand().size() == 0){
                    JOptionPane.showMessageDialog(null, "Yay!! Congratz on the win " + playerThree.getName());
                }
                System.exit(0); //end program
                break;

            case 4: // create 4 player's hands

                enterFirstThreePlayersNames(); //enter first three player's names

                System.out.print("Enter fourth player name: "); //enter 4th player's name
                input = new Scanner(System.in);
                playerNameFour = input.nextLine();
                playerNames [3] = playerNameFour;

                playerNumber = (int)(Math.random() * numberOfPlayers); //randomly choose player 1, the following players are in order of entry

                buildFirstThreePlayers();

                incrementPlayerNumber();
                playerFour = new Player(playerNames[playerNumber]); //build 4th player
                players[playerNumber] = playerFour;

                for(int i=0; i < NUMBER_OF_CARDS_PER_HAND; ++i){ //add 8 cards to each hand and delete those cards from deck

                    fillThreeHands();
                    playerFour.getHand().add(shuffledDeck.get(0));
                    shuffledDeck.remove(0);
                }
                if(gos == 0) { //first player plays

                    firstPlayOfGame();
                }
                do{
                    compare = 0; //set compare to false
                    incrementPlayerNumber(); //next player
                    playerWaitToStart(); //wait til player is ready
                    holdPlayerNumber = playerNumber; //hold playerNumber to reset after cycle

                    if(players[playerNumber].getPickUpCard() == 1 && (players[incrementPlayerNumber()].getPickUpCard() +
                            players[incrementPlayerNumber()].getPickUpCard() + players[incrementPlayerNumber()].getPickUpCard()) <= 1){ //if player has picked up and 1 or less
                                                                                        // of the remaining 3 players have picked up in the last round then player must pass

                        playerNumber = holdPlayerNumber; //reset playerNumber after check for pass conditions
                        JOptionPane.showMessageDialog(null, players[playerNumber].getName() + ", you have picked up from the deck recently" +
                                "\nMore then 1 player has discarded per round since you picked up" +
                                "\nNo Trump Card has been thrown since you picked up" +
                                "\nYou therefore must pass this turn");
                    }else{

                        playerNumber = holdPlayerNumber; //reset playerNumber after check for pass conditions

                        do { //get card and check whether selected card category value > active card value

                            card = selectPlayerCard(players[playerNumber]); //return list selection

                            if (card == players[playerNumber].getHand().size()) { //pick up from deck

                                players[playerNumber].setPickUpCard(1); //card picked up

                                if (shuffledDeck.size() == 0) { //if there are no more cards to pick up

                                    hold = new Card[1]; //create holding array
                                    hold[0] = discardedCards.get(0); //add active card to array
                                    discardedCards.remove(0); //remove active card from discard pile

                                    for (int i = discardedCards.size() - 1; i >= 0; --i) { //transfer discards to shuffled deck

                                        shuffledDeck.add(discardedCards.get(i)); //add card to deck
                                        discardedCards.remove(i); //remove card from pile
                                    }
                                    Collections.shuffle(shuffledDeck); //shuffle the deck
                                    discardedCards.add(hold[0]); //add active card to empty discard pile
                                }
                                players[playerNumber].getHand().add(shuffledDeck.get(0)); //get another card
                                shuffledDeck.remove(0); //remove card from deck
                                compare = 1; //allow

                            } else {
                                compare = compareValues(hand.get(card)); //compare card selected to active card

                                if (compare == 1) {
                                    players[playerNumber].setPickUpCard(0); //set value to card thrown
                                    discardedCards.add(0, hand.get(card)); //add chosen card to discard pile
                                    hand.remove(card); //remove card from hand
                                }
                            }
                        } while (compare == 0); //loop until compare != 0
                        activeCard = discardedCards.get(0); //select active card after throw

                        if(activeCard.getName().startsWith("The ")){ //if trump card thrown, reset all pickUpCard values

                            holdPlayerNumber = playerNumber; //hold playerNumber to reset after cycle
                            players[playerNumber].setPickUpCard(0);
                            players[incrementPlayerNumber()].setPickUpCard(0);
                            players[incrementPlayerNumber()].setPickUpCard(0);
                            players[incrementPlayerNumber()].setPickUpCard(0);
                            playerNumber = holdPlayerNumber; //reset playerNumber
                        }
                        activeCardNotice = getActiveCardValues(); //add to display to show the active category and value
                    }

                }while(playerOne.getHand().size() > 0 && playerTwo.getHand().size() > 0 && playerThree.getHand().size() > 0
                        && playerFour.getHand().size() > 0); //loop until end of game
                JOptionPane.showMessageDialog(null, "Game Over");
                //display winner
                if(playerOne.getHand().size() == 0){
                    JOptionPane.showMessageDialog(null, "Yay!! Congratz on the win " + playerOne.getName());
                }else if(playerTwo.getHand().size() == 0){
                    JOptionPane.showMessageDialog(null, "Yay!! Congratz on the win " + playerTwo.getName());
                }else if(playerThree.getHand().size() == 0){
                    JOptionPane.showMessageDialog(null, "Yay!! Congratz on the win " + playerThree.getName());
                }else if(playerFour.getHand().size() == 0) {
                    JOptionPane.showMessageDialog(null, "Yay!! Congratz on the win " + playerFour.getName());
                }
                System.exit(0); //end program
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

                buildFirstThreePlayers();

                incrementPlayerNumber();
                playerFour = new Player(playerNames[playerNumber]); //build 4th player
                players[playerNumber] = playerFour;

                incrementPlayerNumber();
                playerFive = new Player(playerNames[playerNumber]); //build 5th player
                players[playerNumber] = playerFive;

                for(int i=0; i < NUMBER_OF_CARDS_PER_HAND; ++i){ //add 8 cards to each hand and delete those cards from deck

                    fillThreeHands();
                    playerFour.getHand().add(shuffledDeck.get(0));
                    shuffledDeck.remove(0);
                    playerFive.getHand().add(shuffledDeck.get(0));
                    shuffledDeck.remove(0);
                }
                if(gos == 0) { //first player plays

                    firstPlayOfGame();
                }
                do{
                    compare = 0; //set compare to false
                    incrementPlayerNumber(); //next player
                    playerWaitToStart(); //wait til player is ready
                    holdPlayerNumber = playerNumber; //hold playerNumber to reset after cycle

                    if(players[playerNumber].getPickUpCard() == 1 && (players[incrementPlayerNumber()].getPickUpCard() +
                            players[incrementPlayerNumber()].getPickUpCard() + players[incrementPlayerNumber()].getPickUpCard() +
                            players[incrementPlayerNumber()].getPickUpCard()) <= 2){ //if player has picked up and 2 or less
                                            //of the remaining 4 players have picked up in the last round then player must pass

                        playerNumber = holdPlayerNumber; //reset playerNumber after check for pass conditions
                        JOptionPane.showMessageDialog(null, players[playerNumber].getName() + ", you have picked up from the deck recently" +
                                "\nMore then 1 player has discarded per round since you picked up" +
                                "\nNo Trump Card has been thrown since you picked up" +
                                "\nYou therefore must pass this turn");
                    }else{

                        playerNumber = holdPlayerNumber; //reset playerNumber after check for pass conditions

                        do { //get card and check whether selected card category value > active card value

                            card = selectPlayerCard(players[playerNumber]); //return list selection

                            if (card == players[playerNumber].getHand().size()) { //pick up from deck

                                players[playerNumber].setPickUpCard(1); //card picked up

                                if (shuffledDeck.size() == 0) { //if there are no more cards to pick up

                                    hold = new Card[1]; //create holding array
                                    hold[0] = discardedCards.get(0); //add active card to array
                                    discardedCards.remove(0); //remove active card from discard pile

                                    for (int i = discardedCards.size() - 1; i >= 0; --i) { //transfer discards to shuffled deck

                                        shuffledDeck.add(discardedCards.get(i)); //add card to deck
                                        discardedCards.remove(i); //remove card from pile
                                    }
                                    Collections.shuffle(shuffledDeck); //shuffle the deck
                                    discardedCards.add(hold[0]); //add active card to empty discard pile
                                }
                                players[playerNumber].getHand().add(shuffledDeck.get(0)); //get another card
                                shuffledDeck.remove(0); //remove card from deck
                                compare = 1; //allow

                            } else {
                                compare = compareValues(hand.get(card)); //compare card selected to active card

                                if (compare == 1) {
                                    players[playerNumber].setPickUpCard(0); //set value to card thrown
                                    discardedCards.add(0, hand.get(card)); //add chosen card to discard pile
                                    hand.remove(card); //remove card from hand
                                }
                            }
                        } while (compare == 0); //loop until compare != 0
                        activeCard = discardedCards.get(0); //select active card after throw

                        if(activeCard.getName().startsWith("The ")){ //if trump card thrown, reset all pickUpCard values

                            holdPlayerNumber = playerNumber; //hold playerNumber to reset after cycle
                            players[playerNumber].setPickUpCard(0);
                            players[incrementPlayerNumber()].setPickUpCard(0);
                            players[incrementPlayerNumber()].setPickUpCard(0);
                            players[incrementPlayerNumber()].setPickUpCard(0);
                            players[incrementPlayerNumber()].setPickUpCard(0);
                            playerNumber = holdPlayerNumber; //reset playerNumber

                        }
                        activeCardNotice = getActiveCardValues(); //add to display to show the active category and value
                    }

                }while(playerOne.getHand().size() > 0 && playerTwo.getHand().size() > 0 && playerThree.getHand().size() > 0
                        && playerFour.getHand().size() > 0 && playerFive.getHand().size() > 0); //loop until end of game

                JOptionPane.showMessageDialog(null, "Game Over"); //end game panel
                //display winner
                if(playerOne.getHand().size() == 0){
                    JOptionPane.showMessageDialog(null, "Yay!! Congratz on the win " + playerOne.getName());
                }else if(playerTwo.getHand().size() == 0){
                    JOptionPane.showMessageDialog(null, "Yay!! Congratz on the win " + playerTwo.getName());
                }else if(playerThree.getHand().size() == 0){
                    JOptionPane.showMessageDialog(null, "Yay!! Congratz on the win " + playerThree.getName());
                }else if(playerFour.getHand().size() == 0) {
                    JOptionPane.showMessageDialog(null, "Yay!! Congratz on the win " + playerFour.getName());
                }else if(playerFive.getHand().size() == 0) {
                    JOptionPane.showMessageDialog(null, "Yay!! Congratz on the win " + playerFive.getName());
                }
                System.exit(0); //end program
                break;
        }
    }
    public static int playGameMenu(){ //play game or quit

        int number = 0;
        do {
            try{
                menu = JOptionPane.showInputDialog(null, "THE RULES\nThe number of players and their names are entered first" +
                        "\nThe first player is randomly chosen and the player sequence is displayed" +
                        "\nThe first player must select one of the 5 categories then select their discard to start the game" +
                        "\nCategories are: Hardness, Specific Gravity, Cleavage, Crustal Abundance and Economic Value" +
                        "\nAfter the first play, the active card name, category and value to beat are listed at the top of the selection panel" +
                        "\n\nValues of Hardness and Specific Gravity are numerical and you must discard a higher value than displayed at the top of the selection panel" +
                        "\n\nValues of Cleavage from low to high: \"none\", \"poor/none\", \"1 poor\", \"2 poor\", \"1 good\", \"1 good, 1 poor\", \"2 good\", \"3 good\",\n" +
                        "                              \"1 perfect\", \"1 perfect, 1 good\", \"1 perfect, 2 good\", \"2 perfect, 1 good\", \"3 perfect\", \"4 perfect\", \"6 perfect\"" +
                        "\n\nValues of Crustal Abundance from low to high: \"ultratrace\", \"trace\", \"low\", \"moderate\", \"high\", \"very high\"" +
                        "\n\nValues of Economic Value from low to high: \"trivial\", \"low\", \"moderate\", \"high\", \"very high\", \"I'm rich!\"" +
                        "\n\nFamiliarize yourself with the Cleavage, Crustal Abundance and Economic Value value sequences above for a moment" +
                        "\nTrump Cards reset the category and the value" +
                        "\nIf there is no category value, you can discard any card" +
                        "\nMake your selection by entering the number beside your choice in the selection panel input" +
                        "\nIf you need to pass, you will be informed" +
                        "\nIf you cannot throw a card, you must pick up" +
                        "\nWhen you discard, you must state your card name, active category and value" +
                        "\nIf your selection is out of range or your discard is deemed smaller than the active value, you will need to choose again" +
                        "\n\nDo you wish to:\n1. Play Game\n2. Quit Game");

                number = Integer.parseInt(menu);

            } catch (Exception e) { //catch an input that isn't an integer

                JOptionPane.showMessageDialog(null, "You must enter an integer 1-2");
            }
        }while(number != 1 && number != 2);
        return number;
    }
    public static void enterFirstThreePlayersNames(){ //add names for 3 players

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
    public static void buildFirstThreePlayers(){ //build three players using the names in order of play; add names to playerOrder array

        playerOne = new Player(playerNames[playerNumber]);
        players[playerNumber] = playerOne;

        incrementPlayerNumber();
        playerTwo = new Player(playerNames[playerNumber]);
        players[playerNumber] = playerTwo;

        incrementPlayerNumber();
        playerThree = new Player(playerNames[playerNumber]);
        players[playerNumber] = playerThree;
    }
    public static void fillThreeHands(){ //add cards to first three players hands

        playerOne.getHand().add(shuffledDeck.get(0));
        shuffledDeck.remove(0);
        playerTwo.getHand().add(shuffledDeck.get(0));
        shuffledDeck.remove(0);
        playerThree.getHand().add(shuffledDeck.get(0));
        shuffledDeck.remove(0);
    }
    public static void displayPlayerSequence(){ //display the players names in order of play

        if(numberOfPlayers == 3) {

            String playerSequence = "The order of play is " + playerNames[playerNumber] + ", " + playerNames[incrementPlayerNumber()] + ", "
                    + playerNames[incrementPlayerNumber()];
            incrementPlayerNumber(); //complete playerNumber cycle
            JOptionPane.showMessageDialog(null, playerSequence);

        }else if(numberOfPlayers == 4) {

            String playerSequence = "The order of play is " + playerNames[playerNumber] + ", " + playerNames[incrementPlayerNumber()] + ", "
                    + playerNames[incrementPlayerNumber()] + ", " + playerNames[incrementPlayerNumber()];
            incrementPlayerNumber(); //complete playerNumber cycle
            JOptionPane.showMessageDialog(null, playerSequence);

        }else if(numberOfPlayers == 5) {

            String playerSequence = "The order of play is " + playerNames[playerNumber] + ", " + playerNames[incrementPlayerNumber()] + ", "
                    + playerNames[incrementPlayerNumber()] + ", " + playerNames[incrementPlayerNumber()] + ", " + playerNames[incrementPlayerNumber()];
            incrementPlayerNumber(); //complete playerNumber cycle
            JOptionPane.showMessageDialog(null, playerSequence);
        }
    }
    public static void playerWaitToStart(){ //wait for player to respond so cards are not visible to other players

        JOptionPane.showMessageDialog(null, playerNames[playerNumber] + " press OK when you are ready to play");
    }
    public static int selectFirstCategory(Player player){ //view first player hand and select category

        int number = 0;
        hand = player.getHand();
        handCards = player.getHand().size();
        StringBuilder message = new StringBuilder();

        for(int i = 0; i < handCards; ++i){ //append each card in hand to display

            String script = "\n" + hand.get(i);
            message.append(script);
        }
        message.append("\nChoose a category (1-5): \n"); //add category question
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
                choice = JOptionPane.showInputDialog(null, playerNames[playerNumber] + "'s cards are: " + message);
                number = Integer.parseInt(choice); //make choice an integer

            } catch (Exception e) { //catch an input that isn't an integer

                JOptionPane.showMessageDialog(null, "You must enter an integer 1-5");
            }
            if(number < 0 || number > categories.length){ //if choice is outside range

                JOptionPane.showMessageDialog(null, "You must enter an integer 1-" + categories.length);
            }
        }while(number < 1 || number > categories.length); //category choice is within range
        return number - 1; //change number value to element number
    }
    static public int checkCategory(int category){ //check if selected category is wanted

        return JOptionPane.showConfirmDialog(null, "You have chosen " + categories[category] +
                "\nIs this correct?", "Category Accept/Reject", JOptionPane.YES_NO_OPTION);
    }
    public static int selectFirstCard(){ //select first card to throw

        int count = 1;
        int number = 0;
        handCards = hand.size();
        StringBuilder message = new StringBuilder(); //build card display

        message.append("\nThe Active category is "); //print active card and chosen active category
        message.append(activeCategory);
        message.append("\n\n");
        message.append(playerNames[playerNumber] + "'s cards are: ");

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
            if(hand.get(number-1).getName().startsWith("The ")){

                JOptionPane.showMessageDialog(null, "You cannot throw a trump on the first go");
                number = -1;
            }
        }while(number < 1 || number > (hand.size() + 2)); //choice is within range
        return number - 1; //change to element number
    }
    static String getActiveCardValues(){ //build message at top of selection display panel

        stringBuilder = new StringBuilder(); //build message

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

                    category = selectFirstCategory(players[playerNumber]); //display cards and select category
                    question = checkCategory(category); //check selected category

                }while(question != 0); //until category is accepted

                activeCategory = categories[category];
            }
            stringBuilder.append("\nThe Active category is "); //add category to message
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

            //select message for each category
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
    public static int incrementPlayerNumber(){

        ++playerNumber;
        if(playerNumber > numberOfPlayers - 1){

            playerNumber = 0;
        }
        return playerNumber;
    }
    public static int selectPlayerCard(Player player){ //player selects card from displayed hand

        hand = player.getHand(); //get current player's hand
        handCards = player.getHand().size(); //get number of cards
        int count = 1;
        int number = 0;
        StringBuilder message = new StringBuilder();

        for(int i = 0; i < handCards; ++i){ //build player card selection display

            String script = "\n" + count + ".   " + player.getHand().get(i);
            message.append(script);
            ++count;
        }
        message.append("\n");
        message.append(count);
        message.append(". Pick a card from deck");
        message.append("\n\n");
        message.append("Please enter a number 1-");
        message.append(count);

        do { //continue to choose the category number while selection is not among the choices
            try{
                choice = JOptionPane.showInputDialog(null,activeCardNotice + "\n" + playerNames[playerNumber] + "'s cards are: " + message);
                number = Integer.parseInt(choice);

            } catch (Exception e) { //catch an input that isn't an integer

                JOptionPane.showMessageDialog(null, "You must enter an integer 1-" + (handCards + 1));
            }
        }while(number < 1 || number > handCards + 2);
        return number - 1; //change value to element number
    }
    public static int compareValues(Card card){ //compare active card and chosen card

        if(activeCard.getName().startsWith("The ") || card.getName().startsWith("The ")){ //skip trump cards

            compare = 1;

        }else if(activeCategory.equals("Hardness")){ //compare hardness values

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

            if (index1 > index2){ // if card crustal abundance is higher than active card

                compare = 1;
            }
        }else if(activeCategory.equals("Economic Value")) { //compare economic value values

            int index1 = Arrays.asList(economicValueHierarchy).indexOf(((MineralCard) card).getEconomicValue()); //get index number of played card in array
            int index2 = Arrays.asList(economicValueHierarchy).indexOf(((MineralCard) activeCard).getEconomicValue()); //get index number of active card in array

            if (index1 > index2){ // if card economic value is higher than active card

                compare = 1;
            }
        }
        if(compare == 0){

            JOptionPane.showMessageDialog(null, "You cannot throw that card" +
                                        "\nCheck Category\nCheck Value\nTry again");
        }
        return compare;
    }
    public static void firstPlayOfGame(){

        displayPlayerSequence(); //window showing the players in order of play
        playerWaitToStart(); //wait til player is ready

        do { //view cards and select active category until satisfied

            category = selectFirstCategory(players[playerNumber]); //display cards and select category
            question = checkCategory(category); //check selected category

        }while(question != 0); //loop until category is accepted

        activeCategory = categories[category]; //set active category
        card = selectFirstCard(); //select card to throw
        players[playerNumber].setPickUpCard(0); //card not picked up
        discardedCards.add(0, hand.get(card)); //add chosen card to discard pile
        hand.remove(card); //remove card from hand
        activeCard = discardedCards.get(0); //select active card
        activeCardNotice = getActiveCardValues(); //add to display to show the active category and value

        gos = 1; //end first player turn
    }
}
