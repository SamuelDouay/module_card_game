package game.dutch;

import game.dutch.enums.CardColor;
import game.dutch.enums.CardValue;
import game.dutch.util.Card;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class Dutch {
    private final Stack<Card> pickaxe;
    private final Stack<Card> discard;
    private static final int CARD_INIT = 4;

    public Dutch() {
        this.pickaxe = new Stack<>();
        this.discard = new Stack<>();
    }

    public void initGame() {
        Card c;
        for(CardColor cardColor : CardColor.values()) {
            for (CardValue cardValue : CardValue.values()) {
                c = new Card(cardValue, cardColor, cardValue.ordinal() + 1);
                if (ifCardRedAndKing(cardColor, cardValue)){
                    c = new Card(cardValue, cardColor, 0);
                }
                this.pickaxe.push(c);
            }
        }
        Collections.shuffle(this.pickaxe);
    }

    private boolean ifCardRedAndKing(CardColor cardColor, CardValue cardValue) {
        return (CardValue.KING.equals(cardValue)) && (cardColor.equals(CardColor.HEART) || cardColor.equals(CardColor.DIAMOND));
    }

    public List<Card> discardCardToPlayers() {
        List<Card> cards = new ArrayList<>();
        for (int i = 0; i < CARD_INIT; i++) {
            cards.add(this.pickaxe.pop());
        }
        return cards;
    }

    public Stack<Card> getPickaxe() {
        return pickaxe;
    }

    public Stack<Card> getDiscard() {
        return discard;
    }
}
