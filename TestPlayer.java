import java.util.ArrayList;

/**
 * Created by tony on 21/08/2016.
 */
public class TestPlayer { //create 2 players, deal 8 cards to each player and print player's hands

    public static void main(String[] args) {

        Player player = new Player("Kim Pink"); //create player 1
        Player player1 = new Player("Bob"); //create player 2
        ArrayList <Card> hand = player.getHand(); //create and arrayList for player 1
        ArrayList <Card> hand1 = player1.getHand(); //create hand arrayList for player 2

        System.out.println(player.getName()); //test names
        System.out.println(player1.getName());

        Deck deck = new Deck(); //create deck of cards

        for(int i = 0; i < 16; ++i){ //deal cards alternately to each player

           if(i % 2 != 0) {
               hand1.add(deck.cards[i]);
           }else{
               hand.add(deck.cards[i]);
           }
        }
        for(int i = 0; i < 8; ++i){ //print player's hands

            System.out.print(hand.get(i).getName());
            System.out.println("    \t" + hand1.get(i).getName());
        }
    }
}
