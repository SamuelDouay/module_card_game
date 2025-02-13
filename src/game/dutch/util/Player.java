package game.dutch.util;

import java.util.List;
import java.util.Stack;

public class Player {
    private int id;
    private List<Card> hand;
    private int point;
    private boolean dutch;

    public Player(int id, List<Card> hand) {
        this.id = id;
        this.hand = hand;
        this.dutch = false;
        this.point = 0;
        this.calculatePoint();
    }

    private void calculatePoint() {
        for (Card c: this.hand ) {
            this.point += c.getPoint();
        }
    }

    public int getId() {
        return id;
    }

    public String lookCard(int id) {
        return this.hand.get(id-1).lookCard();
    }

    public void addCard(Card card) {
        this.hand.add(card);
    }

    public int getPoint() {
        this.calculatePoint();
        return point;
    }

    public boolean isDutch() {
        return dutch;
    }

    public void shoutDutch() {
        this.dutch = true;
    }

    public Card pick(Stack<Card> pickaxe) {
        Card res = pickaxe.pop();
        res.setWatching(true);
        return res;
    }

    public Card discard(int indexCard) {
       return this.hand.remove(indexCard - 1);
    }

    public void recoveryCard(int indexCard, Card recoveryCard) {
        this.hand.add(indexCard - 1, recoveryCard);
    }

    public Card exchangeCard(Card cardPickaxe, int indexCard) {
        return this.hand.set(indexCard - 1, cardPickaxe);
    }

    @Override
    public String toString() {
        StringBuilder display = new StringBuilder();
        
        // Ligne du haut des cartes
        display.append("Main du Joueur ").append(this.id).append(" (").append(this.point).append(" points)\n\n");
        
        // Numéros des cartes
        for (int i = 1; i <= hand.size(); i++) {
            display.append(String.format("   [%d]    ", i));
        }
        display.append("\n");
        
        // Affichage des cartes ligne par ligne
        String[] cardLines = new String[6];
        for (Card card : hand) {
            String[] lines = card.toString().split("\n");
            for (int i = 0; i < lines.length; i++) {
                if (cardLines[i] == null) cardLines[i] = "";
                cardLines[i] += lines[i] + " ";
            }
        }
        
        // Assemblage des lignes
        for (String line : cardLines) {
            display.append(line).append("\n");
        }
        
        return display.toString();
    }
}
