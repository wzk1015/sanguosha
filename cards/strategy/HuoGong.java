package cards.strategy;

import cards.Card;
import cards.Color;
import cards.Strategy;
import cards.basic.HurtType;
import manager.GameManager;
import manager.IO;

public class HuoGong extends Strategy {

    public HuoGong(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        if (!gotWuXie()) {
            Card c = getTarget().chooseCard(getTarget().getCards());
            IO.printCard(c);
            if (getSource().requestColor(c.color())) {
                int realNum = getTarget().hurt(this, getSource(), 1, HurtType.fire);
                GameManager.linkHurt(this, getSource(), realNum, HurtType.fire);
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "火攻";
    }

    @Override
    public boolean needChooseTarget() {
        return true;
    }
}
