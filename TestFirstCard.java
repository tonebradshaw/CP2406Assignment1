import javax.swing.*;

/**
 * Created by tony on 3/09/2016.
 */
public class TestFirstCard {

    static Deck deck;
    static Card [] hand;
    static int handCards, category, question;
    static String choice;
    static String [] categories  = {"Hardness", "Specific Gravity", "Cleavage", "Crustal Abundance", "Economic Value"};

    public static void main(String[] args) {

        deck = new Deck(); //make deck
        hand = new Card [8]; //make empty hand array

        for(int i = 0; i < 8; ++i){ //load hand

            hand[i] = deck.cards[i];
        }
        do { //view cards and select active category until satisfied

            category = selectFirstCategory(); //display cards and select category
            question = checkCategory(category); //check selected category

        }while(question != 0); //until category is accepted

        int card = selectFirstCard(); //select card to throw
        System.out.println("Active Card is :\n" + hand[card] +
                "\nand the Active category is " + categories[category]); //print active card and chosen active category
    }
    public static int selectFirstCategory(){ //view hand and select category

        int number = 0;
        handCards = hand.length;
        StringBuilder message = new StringBuilder();

        for(int i = 0; i < hand.length; ++i){ //append each card to hand to display

            String script = "\n" + hand[i];
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
            if(number < 1 || number > categories.length){

                JOptionPane.showMessageDialog(null, "You must enter an integer 1-" + categories.length);
            }
        }while(number < 1 || number > categories.length);
        return number - 1; //change to element number
    }
    static public int checkCategory(int category){ //check if selected category is wanted

        return JOptionPane.showConfirmDialog(null, "You have chosen " + categories[category] +
                "\nIs this correct?", "You have chosen " + categories[category] +
                "\nIs this correct?", JOptionPane.YES_NO_OPTION);
    }
    public static int selectFirstCard(){ //select first card to throw

        int count = 1;
        int number = 0;
        handCards = hand.length;
        StringBuilder message = new StringBuilder();

        for(int i = 0; i < handCards; ++i){ //display hand with selection numbers

            String script = "\n" + count + " - " + hand[i];
            message.append(script);
            ++count;
        }
        message.append("\nChoose a card 1-");
        message.append(hand.length);

        do { //continue until an acceptable card is chosen
            try{
                choice = JOptionPane.showInputDialog(null, message);
                number = Integer.parseInt(choice);

            } catch (Exception e) { //catch an input that isn't an integer

                JOptionPane.showMessageDialog(null, "You must enter an integer 1-" + hand.length);
            }
            if(number < 1 || number > hand.length){

                JOptionPane.showMessageDialog(null, "You must enter an integer 1-" + hand.length);
            }
        }while(number < 1 || number > hand.length);
        return number - 1; //change to element number
    }
}
