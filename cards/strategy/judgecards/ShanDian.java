package cards.strategy.judgecards;

import cards.Card;
import cards.Color;
import cards.JudgeCard;
import cards.basic.HurtType;
import cardsheap.CardsHeap;
import manager.GameManager;

import java.util.ArrayList;

public class ShanDian extends JudgeCard {
    public ShanDian(Color color, int number) {
        super(color, number, 100);
    }

    @Override
    public String use() {
        if (!gotWuXie()) {
            Card judge = CardsHeap.judge(getTarget());
            if (judge.color() == Color.SPADE && judge.number() >= 2 && judge.number() <= 9) {
                int realNum = getTarget().hurt((Card) null, null, 3, HurtType.thunder);
                ArrayList<Card> cs = new ArrayList<>();
                cs.add(this);
                GameManager.linkHurt(cs, null, realNum, HurtType.thunder);
            } else {
                GameManager.moveShanDian(this, getTarget());
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "闪电";
    }
}
