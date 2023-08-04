package game.demineur;

import game.demineur.util.Plateau;

import java.util.Scanner;

public class Main {
    static public void main(String[] args) {
        Plateau p = new Plateau(10, 10, 0.10);
        p.initPlateau();
        System.out.println(p);
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.println("tapes une colonne");
            int colone = scanner.nextInt();

            System.out.println("tapes une ligne");
            int ligne = scanner.nextInt();

            p.showCase(colone,ligne);
            System.out.println(p);
        }
    }
}
