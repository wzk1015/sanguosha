package cards.strategy.judgecards;

import cards.Card;
import cards.Color;
import cards.JudgeCard;
import cards.basic.HurtType;
import cardsheap.CardsHeap;

public class ShanDian extends JudgeCard {
    public ShanDian(Color color, int number) {
        super(color, number, 100);
    }

    @Override
    public String use() {
        if (!gotWuXie()) {
            Card judge = CardsHeap.judge();
            if (judge.color() == Color.SPADE && judge.number() >= 2 && judge.number() >= 9) {
                getTarget().hurt(3, HurtType.thunder);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "闪电";
    }
}
