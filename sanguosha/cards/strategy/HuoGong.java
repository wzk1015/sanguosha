package sanguosha.cards.strategy;

import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.cards.Strategy;
import sanguosha.cards.basic.HurtType;
import sanguosha.manager.GameManager;
import sanguosha.manager.IO;

import java.util.ArrayList;

public class HuoGong extends Strategy {

    public HuoGong(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        if (!gotWuXie(getTarget())) {
            Card c = getTarget().chooseCard(getTarget().getCards());
            IO.printCard(c);
            if (getSource().requestColor(c.color()) != null) {
                int realNum = getTarget().hurt(getThisCard(), getSource(), 1, HurtType.fire);
                ArrayList<Card> cs = new ArrayList<>();
                cs.add(this);
                GameManager.linkHurt(cs, getSource(), realNum, HurtType.fire);
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
