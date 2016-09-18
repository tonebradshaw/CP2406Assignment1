import java.util.ArrayList;

/**
 * Created by tony on 19/08/2016.
 */
public class Player {

    private String name;
    private ArrayList <Card> hand;
    private int pickupCardValue;
    private int throwCard;

    public Player(String name){ //player constructor

        this.name = name;
        hand  = new ArrayList<>();
    }
    public String getName(){ //retrieve player's name

        return name;
    }
    public ArrayList <Card> getHand(){ //retrieve arrayList for player's hand

        return hand;
    }
    public void setPickUpCard(int pickupCardValue){

        this.pickupCardValue = pickupCardValue;
    }
    public int getPickUpCard(){

        return pickupCardValue;
    }
}
