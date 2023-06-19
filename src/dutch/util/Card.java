package dutch.util;

import dutch.enums.CardColor;
import dutch.enums.CardValue;

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

    @Override
    public String toString() {
        return "X";
    }
}
