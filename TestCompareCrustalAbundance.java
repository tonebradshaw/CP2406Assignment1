import java.util.Arrays;

/**
 * Created by tony on 24/08/2016.
 */
public class TestCompareCrustalAbundance {

    static Deck deck;

    public static void main(String[] args) { //create deck of cards, randomly select 2 adjacent cards and compare their crustal abundance locations in the array

        String [] crustalAbundanceHierarchy = {"ultratrace", "trace", "low", "moderate", "high", "very high"}; //make crustal abundance array - low to high

        deck = new Deck(); //make deck

        int number = getNumber(); //select the first card position number

        int index1 = Arrays.asList(crustalAbundanceHierarchy).indexOf(((MineralCard) deck.cards[number]).getCrustalAbundance()); //get index number of 1st card in array
        int index2 = Arrays.asList(crustalAbundanceHierarchy).indexOf(((MineralCard) deck.cards[number + 1]).getCrustalAbundance()); //get index number of 2nd card in array

        if (index1 == index2){ // if locations of value in crustal abundance array are equal

            System.out.println((deck.cards[number]).getName() + " has the same value \"" +
                    ((MineralCard) deck.cards[number]).getCrustalAbundance() + "\" of Crustal Abundance as " + (deck.cards[number + 1]).getName() +
                    " whose value is \"" + ((MineralCard) deck.cards[number + 1]).getCrustalAbundance() + "\"");

        } else if (index1 > index2){ //if locations of value in crustal abundance of first card > than second card

            System.out.println(deck.cards[number].getName() + " has a greater value \"" +
                    ((MineralCard) deck.cards[number]).getCrustalAbundance() + "\" of Crustal Abundance than " + deck.cards[number + 1].getName() +
                    " whose value is \"" + ((MineralCard) deck.cards[number + 1]).getCrustalAbundance() + "\"");

        } else { //if locations of value in crustal abundance of second card > than first card

            System.out.println((deck.cards[number + 1]).getName() + " has a greater value \"" +
                    ((MineralCard) deck.cards[number + 1]).getCrustalAbundance() + "\" of Crustal Abundance than " + deck.cards[number].getName() +
                    " whose value is \"" + ((MineralCard) deck.cards[number]).getCrustalAbundance() + "\"");
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
