package cards.strategy.judgecards;

import cards.Color;
import cards.JudgeCard;
import cardsheap.CardsHeap;

public class LeBuSiShu extends JudgeCard {
    public LeBuSiShu(Color color, int number) {
        super(color, number);
    }

    @Override
    public String use() {
        if (!gotWuXie()) {
            if (CardsHeap.judge().color() != Color.HEART) {
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
