import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by tony on 19/08/2016.
 */
public class Player {

    private String name;
    private ArrayList <Card> hand;

    public Player(String name){ //player constructor

        this.name = name;
    }
    public String getName(){ //retrieve player's name

        return name;
    }
    public ArrayList getHand(){ //retrieve arrayList for player's hand

        hand = new ArrayList<>();
        return hand;
    }
}
