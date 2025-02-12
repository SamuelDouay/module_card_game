package game.dutch.util;

import game.dutch.enums.CardColor;
import game.dutch.enums.CardValue;

import java.util.Objects;

public class Card {
    private CardValue value;
    private CardColor color;
    private int point;
    private boolean isWatching;

    public Card(CardValue value, CardColor color, int point) {
        this.value = value;
        this.color = color;
        this.point = point;
        this.isWatching = false;
    }

    public String getValue() {
        return value.toString();
    }

    public String getColor() {
        return color.toString();
    }

    public int getPoint() {
        return point;
    }

    public boolean isWatching() {
        return isWatching;
    }

    public void setWatching(boolean isWatching) {
        this.isWatching = isWatching;
    }

    public String lookCard() {
        this.setWatching(true);
        return this.getValue() + " de " + this.getColor();
    }
    @Override
    public String toString() {
        return "X";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card card)) return false;
        return card.value.equals(value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    public void ifPresent(Object object) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ifPresent'");
    }
}
