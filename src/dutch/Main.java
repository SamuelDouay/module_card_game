package dutch;

import dutch.util.Player;

//import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome au  dutch");

        Dutch dutch = new Dutch();
        dutch.initGame();

        Player p1 = new Player(0, dutch.discardCardToPlayers());
        System.out.println(p1);

        System.out.println("Bye");

    }
}
