package cards.strategy;

import cards.Card;
import cards.Color;
import cards.Strategy;
import cards.basic.HurtType;
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
                getTarget().hurt(1, HurtType.fire);
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
