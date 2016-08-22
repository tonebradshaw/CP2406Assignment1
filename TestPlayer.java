/**
 * Created by tony on 21/08/2016.
 */
public class TestPlayer {

    static Player player;
    static Player player1;

    public static void main(String[] args) {

        player = new Player("Kim Pink");
        player1 = new Player("Bob");

        System.out.println(player.getName());
        System.out.println(player1.getName());
    }
}
