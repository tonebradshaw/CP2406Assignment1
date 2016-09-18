import java.util.ArrayList;

/**
 * Created by tony on 21/08/2016.
 */
public class TestPlayer { //create 2 players, deal 8 cards to each player and print player's hands

    static Player playerOne;
    static Player playerTwo;

    public static void main(String[] args) {

        playerOne = new Player("Kim Pink"); //create player 1
        playerTwo = new Player("Bob"); //create player 2

        System.out.println(playerOne.getName()); //test names
        System.out.println(playerTwo.getName());

        Deck deck = new Deck(); //create deck of cards

        for(int i = 0; i < 16; ++i){ //deal cards alternately to each player

           if(i % 2 != 0) {
               playerTwo.getHand().add(deck.cards[i]);
           }else{
               playerOne.getHand().add(deck.cards[i]);
           }
        }
        printPlayerOneHand();
        printPlayerTwoHand();
    }
    public static void printPlayerOneHand(){

        System.out.println("\n");

        for(int i=0; i<8; ++i) {

            if(((Card)(playerOne.getHand().get(i))).getName().startsWith("The ")){

                System.out.println(((Card)(playerOne.getHand().get(i))).getName() + " - Category: " +
                        ((TrumpCard)playerOne.getHand().get(i)).getCategory()); //print out selected Trump Card attributes
                continue;
            }
            System.out.println(((Card)(playerOne.getHand().get(i))).getName() + " - Hardness: " +
                    ((MineralCard) playerOne.getHand().get(i)).getHardness()); //print out selected Mineral Card attribute
        }
    }
    public static void printPlayerTwoHand(){

        System.out.println("\n");

        for(int i=0; i<8; ++i) {

            if(((playerTwo.getHand().get(i))).getName().startsWith("The ")){

                System.out.println(((playerTwo.getHand().get(i))).getName() + " - Category: " +
                        ((TrumpCard) playerTwo.getHand().get(i)).getCategory()); //print out selected Trump Card attributes
                continue;
            }
            System.out.println(((playerTwo.getHand().get(i))).getName() + " - Hardness: " +
                    ((MineralCard) playerTwo.getHand().get(i)).getHardness()); //print out selected Mineral Card attribute
        }
    }
}
