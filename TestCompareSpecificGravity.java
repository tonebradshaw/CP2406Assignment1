/**
 * Created by tony on 24/08/2016.
 */
public class TestCompareSpecificGravity {

    static Deck deck;

    public static void main(String[] args) { //create deck of cards, randomly select 2 adjacent cards and compare their specific gravity

        deck = new Deck(); //make deck

        int number = getNumber(); //select the first card position number

        double value = ((MineralCard) deck.cards[number]).getSpecificGravity(); //get specific gravity value of first card
        double value1 = ((MineralCard) deck.cards[number + 1]).getSpecificGravity(); //get specific gravity value of second card

        if(value == value1){ //if value of hardness of first card equals second card

            System.out.println((deck.cards[number]).getName() + " has the same value \"" +
                    ((MineralCard) deck.cards[number]).getSpecificGravity() + "\" of Specific Gravity as " + (deck.cards[number + 1]).getName() +
                    " whose value is \"" + ((MineralCard) deck.cards[number + 1]).getSpecificGravity() + "\"");

        } else if (value > value1){ //if value of hardness of first card > than second card

            System.out.println(deck.cards[number].getName() + " has a greater value \"" +
                    ((MineralCard) deck.cards[number]).getSpecificGravity() + "\" of Specific Gravity than " + deck.cards[number + 1].getName() +
                    " whose value is \"" + ((MineralCard) deck.cards[number + 1]).getSpecificGravity() + "\"");

        } else { //if value of hardness of second card > than first card

            System.out.println((deck.cards[number + 1]).getName() + " has a greater value \"" +
                    ((MineralCard) deck.cards[number + 1]).getSpecificGravity() + "\" of Specific Gravity than " + deck.cards[number].getName() +
                    " whose value is \"" + ((MineralCard) deck.cards[number]).getSpecificGravity() + "\"");
        }
    }
    public static int getNumber(){ //select 2 adjacent mineral cards in the deck, skip trump cards

        int number = 1 + (int)(Math.random() * 59);

        while(deck.cards[number].getName().startsWith("The ") ||
                deck.cards[number + 1].getName().startsWith("The ")){ //make sure there are no trump cards

            ++number;
            if(number > 59) {
                number = 50;
            }
        }
        System.out.println("Card number: " + number);
        return number;
    }
}
