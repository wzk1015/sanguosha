package sanguosha.cards.strategy;

import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.cards.Strategy;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.manager.GameManager;
import sanguosha.people.Person;

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
                    c = p.chooseCard(cards, false);
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

    @Override
    public String details() {
        return "出牌阶段，对所有角色使用。" +
                "（选择目标后）你从牌堆顶亮出等同于角色数量的牌，每名目标角色获得这些牌中（剩余的）的任意一张。";
    }
}
