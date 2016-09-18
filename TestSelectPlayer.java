import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by tony on 8/09/2016.
 */
public class TestSelectPlayer {

    static Player playerOne, playerTwo, playerThree;
    static Player [] players = new Player [3];

    public static void main(String[] args) {

        playerOne = new Player("Bill");
        players[0] = playerOne;
        playerTwo = new Player("Jill");
        players[1] = playerTwo;
        playerThree = new Player("Lill");
        players[2] = playerThree;

        System.out.println(players[1].getName());
        selectPlayer(0);
    }
    static void selectPlayer(int number) {

        System.out.println(players[number].getName());
    }
}