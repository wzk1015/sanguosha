package sanguosha.cards.strategy.judgecards;

import sanguosha.cards.Color;
import sanguosha.cards.JudgeCard;
import sanguosha.cardsheap.CardsHeap;

public class LeBuSiShu extends JudgeCard {
    public LeBuSiShu(Color color, int number) {
        super(color, number);
    }

    @Override
    public String use() {
        if (!gotWuXie(getTarget())) {
            if (CardsHeap.judge(getTarget()).color() != Color.HEART) {
                return "skip use";
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "乐不思蜀";
    }

    @Override
    public boolean needChooseTarget() {
        return true;
    }
}
