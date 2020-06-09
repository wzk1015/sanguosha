package cards.strategy.judgecards;

import cards.Color;
import cards.JudgeCard;
import cardsheap.CardsHeap;

public class BingLiangCunDuan extends JudgeCard {
    public BingLiangCunDuan(Color color, int number) {
        super(color, number, 1);
    }

    @Override
    public String use() {
        if (!gotWuXie()) {
            if (CardsHeap.judge(getTarget()).color() != Color.CLUB) {
                return "skip draw";
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "兵粮寸断";
    }

    @Override
    public boolean needChooseTarget() {
        return true;
    }
}
