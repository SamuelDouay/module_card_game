package dutch.util;

import java.util.List;

public class Player {
    private int id;
    private List<Card> hand;
    private int point;
    private boolean dutch;

    public Player(int id, List<Card> hand) {
        this.id = id;
        this.hand = hand;
        this.dutch = false;
        this.calculatePoint();
    }

    private void calculatePoint() {
        int res = 0;
        for (Card c: this.hand ) {
            res += c.getPoint();
        }
        this.point = res;
    }

    public int getId() {
        return id;
    }

    public String lookCard(int id) {
        this.hand.get(id-1).setWatching(true);
        return this.hand.get(id-1).getValue() + " de " + this.hand.get(id-1).getColor();
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

    public Card pick() {
        return null;
    }

    public void discard(Card card) {
    }

    @Override
    public String toString() {
        return "Player : " + this.id + " - Hand : " + this.hand;
    }
}
