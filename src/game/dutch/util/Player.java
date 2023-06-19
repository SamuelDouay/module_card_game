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
        return "Player " + this.id + " - Hand : " + this.hand;
    }
}
