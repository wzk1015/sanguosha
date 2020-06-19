package sanguosha.cards.strategy.judgecards;

import sanguosha.cards.Color;
import sanguosha.cards.JudgeCard;
import sanguosha.cardsheap.CardsHeap;

public class BingLiangCunDuan extends JudgeCard {
    public BingLiangCunDuan(Color color, int number) {
        super(color, number, 1);
    }

    @Override
    public String use() {
        if (!gotWuXie(getTarget())) {
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

    @Override
    public int getDistance() {
        if (getSource().hasDuanLiang()) {
            return 2;
        }
        return super.getDistance();
    }
}
