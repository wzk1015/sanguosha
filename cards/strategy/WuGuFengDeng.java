package cards.strategy;

import cards.Card;
import cards.Color;
import cards.Strategy;
import cardsheap.CardsHeap;
import manager.GameManager;
import people.Person;

import java.util.ArrayList;

public class WuGuFengDeng extends Strategy {
    public WuGuFengDeng(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        ArrayList<Card> cards = CardsHeap.draw(GameManager.getNumPlayers());
        for (Person p : GameManager.getPlayers()) {
            if (!gotWuXie(p)) {
                Card c;
                if (cards.size() == 1) {
                    c = cards.get(0);
                } else {
                    c = p.chooseCard(cards);
                }
                p.addCard(c);
                cards.remove(c);
            }
        }
        if (!cards.isEmpty()) {
            CardsHeap.discard(cards);
        }
        return true;
    }

    @Override
    public String toString() {
        return "五谷丰登";
    }
}
