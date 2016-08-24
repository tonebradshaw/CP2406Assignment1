import java.util.ArrayList;

/**
 * Created by tony on 21/08/2016.
 */
public class TestPlayer {

    public static void main(String[] args) {

        Player player = new Player("Kim Pink");
        Player player1 = new Player("Bob");
        ArrayList <Card> hand = player.getHand();
        ArrayList <Card> hand1 = player1.getHand();

        System.out.println(player.getName());
        System.out.println(player1.getName());

        Deck deck = new Deck();

        for(int i = 0; i < 16; ++i){

           if(i % 2 != 0) {
               hand1.add(deck.cards[i]);
           }else{
               hand.add(deck.cards[i]);
           }
        }
        for(int i = 0; i < 8; ++i){

            System.out.print(hand.get(i).getName());
            System.out.println("    \t" + hand1.get(i).getName());
        }
    }
}
