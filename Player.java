import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by tony on 19/08/2016.
 */
public class Player {

    private String name;
    private ArrayList <Card> hand;

    public Player(String name){

        this.name = name;
    }
    public String getName(){

        return name;
    }

    public ArrayList getHand(){

        hand = new ArrayList<>();
        return hand;
    }
}
