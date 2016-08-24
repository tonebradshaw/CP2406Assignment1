/**
 * Created by tony on 21/08/2016.
 */
public class TestDeck {

    public static void main(String[] args) { //create and print out an unshuffled deck of cards

        Deck deck = new Deck();

        for(int i = 0; i < 60; ++i){

            System.out.println(deck.cards[i]);
        }
    }
}
