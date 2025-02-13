package game.dutch;

import game.dutch.enums.CardValue;
import game.dutch.util.Card;
import game.dutch.util.Player;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome au  game.dutch");
        Dutch dutch = new Dutch();
        dutch.initGame();

        Player p1 = new Player(0, dutch.discardCardToPlayers());
        System.out.println(p1);

        System.out.println("Regardes une carte, tapes son index");
        System.out.println(p1.lookCard(scanner.nextInt()));

        System.out.println("Regardes une deuxième  carte, tapes son index");
        System.out.println(p1.lookCard(scanner.nextInt()));


        while(! p1.isDutch()) {
            System.out.println("Nombre de cartes dans la pioche : " + dutch.getPickaxe().size());
            if (dutch.getDiscard().size() > 0) {
                System.out.println("Dernière carte de la défausse : " + dutch.getDiscard().peek().lookCard());
            }

            Card cardPickaxe = getCardPickaxe(dutch, p1, scanner);

            exchangeCard(scanner, dutch, p1, cardPickaxe);
            throwCard(scanner, dutch, p1, "As tu une autre carte que tu souhaites jetter ? o/n");

            System.out.println("Veux tu crier game.dutch ? o/n");
            String shoutDutch = scanner.next();

            if (shoutDutch.equals("o")) {
                p1.shoutDutch();
            }
        }

        System.out.println("Fin du game");
        System.out.println("Joueur 0 : " + p1.getPoint() + " points");

    }

    private static Card getCardPickaxe(Dutch dutch, Player p1, Scanner scanner) {
        Card pickedCard = p1.pick(dutch.getPickaxe());
        System.out.println("Premier tour - Tu pioches une nouvelle carte : " + pickedCard.lookCard());
        return pickedCard;
    }

    private static void exchangeCard(Scanner scanner, Dutch dutch, Player p, Card cardPickaxe) {
        System.out.println("Veux tu la jetter (j) ou l'échanger (e) ?");
        String choice = scanner.next();

        if(choice.equals("j")) {
            dutch.getDiscard().push(cardPickaxe);
        }else {
            System.out.println(p);
            System.out.println("Avec quel carte veux - tu l'échanger ?");
            System.out.println("Tapes son index :");
            Card cardExchange = p.exchangeCard(cardPickaxe, scanner.nextInt());
            dutch.getDiscard().push(cardExchange);
            System.out.println("Tu viens de jeter cette carte" + cardExchange.lookCard());
        }

        if (dutch.getDiscard().peek().equals(new Card(CardValue.QUEEN, null, 0))) {
            System.out.println("C'est la reine !!!!!");
            System.out.println("Tu as le droit de regarder une carte");
            System.out.println("Regardes une carte, tapes son index");
            System.out.println(p.lookCard(scanner.nextInt()));
            throwCard(scanner, dutch, p, "Souhaites tu la jetter ?  o/n");
        }
    }

    private static void throwCard(Scanner scanner, Dutch dutch, Player p, String question) {
        System.out.println(question);
        String doubleCard = scanner.next();

        if (doubleCard.equals("o")) {
            System.out.println("Tapes son index :");
            int index = scanner.nextInt();
            Card cardDouble = p.discard(index);

            if(cardDouble.equals(dutch.getDiscard().peek())) {
                dutch.getDiscard().push(cardDouble);
                System.out.println("Bien joué !");
            }
            else {
                System.out.println("Tu t'es trompé, reprends ta carte + 2 nouvelles cartes");
                p.recoveryCard(index, cardDouble);
                p.addCard(dutch.getPickaxe().pop());
                p.addCard(dutch.getPickaxe().pop());
            }
        }
    }
}
