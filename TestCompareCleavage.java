import java.util.Arrays;

/**
 * Created by tony on 24/08/2016.
 */
public class TestCompareCleavage {

    static Deck deck;

    public static void main(String[] args) { //create deck of cards, randomly select 2 adjacent cards and compare their cleavage locations in the array

        String [] cleavageHierarchy = {"none", "poor/none", "1 poor", "2 poor", "1 good", "1 good, 1 poor", "2 good", "3 good",
                "1 perfect", "1 perfect, 1 good", "1 perfect, 2 good", "2 perfect, 1 good", "3 perfect", "4 perfect", "6 perfect"}; //make cleavage array - low to high

        deck = new Deck(); //make deck

        int number = getNumber(); //select the first card position number

        int index1 = Arrays.asList(cleavageHierarchy).indexOf(((MineralCard) deck.cards[number]).getCleavage()); //get index number of 1st card in array
        int index2 = Arrays.asList(cleavageHierarchy).indexOf(((MineralCard) deck.cards[number + 1]).getCleavage()); //get index number of 2nd card in array

        if (index1 == index2){ // if locations of value in crustal abundance array are equal

            System.out.println((deck.cards[number]).getName() + " has the same value \"" +
                    ((MineralCard) deck.cards[number]).getCleavage() + "\" of Cleavage as " + (deck.cards[number + 1]).getName() +
                    " whose value is \"" + ((MineralCard) deck.cards[number + 1]).getCleavage() + "\"");

        } else if (index1 > index2){ //if locations of value in crustal abundance of first card > than second card

            System.out.println(deck.cards[number].getName() + " has a greater value \"" +
                    ((MineralCard) deck.cards[number]).getCleavage() + "\" of Cleavage than " + deck.cards[number + 1].getName() +
                    " whose value is \"" + ((MineralCard) deck.cards[number + 1]).getCleavage() + "\"");

        } else { //if locations of value in crustal abundance of second card > than first card

            System.out.println((deck.cards[number + 1]).getName() + " has a greater value \"" +
                    ((MineralCard) deck.cards[number + 1]).getCleavage() + "\" of Cleavage than " + deck.cards[number].getName() +
                    " whose value is \"" + ((MineralCard) deck.cards[number]).getCleavage() + "\"");
        }
    }
    public static int getNumber(){ //select 2 adjacent mineral cards in the deck, skip trump cards

        int number = 1 + (int)(Math.random() * 59);

        while((deck.cards[number]).getName().startsWith("The ") ||
                (deck.cards[number + 1]).getName().startsWith("The ")){ //make sure there are no trump cards

            ++number;
            if(number > 59) {
                number = 50;
            }
        }
        System.out.println("Card number: " + number);
        return number;
    }
}
