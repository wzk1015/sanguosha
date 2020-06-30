package sanguosha.cards.strategy;

import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.cards.Strategy;
import sanguosha.cards.basic.HurtType;
import sanguosha.manager.GameManager;
import sanguosha.manager.IO;
import sanguosha.people.Person;

import java.util.ArrayList;

public class HuoGong extends Strategy {

    public HuoGong(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        if (!gotWuXie(getTarget())) {
            Card c = getTarget().chooseCard(getTarget().getCards(), false);
            IO.printCardPublic(c);
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

    @Override
    public boolean askTarget(Person user) {
        setSource(user);

        while (true) {
            Person p = user.selectPlayer(true);
            if (p == null) {
                return false;
            }
            if (p.getCards().isEmpty()) {
                getSource().printlnToIO("you can't choose a person with no cards");
                continue;
            }
            setTarget(user.selectPlayer());
            return true;
        }
    }

    @Override
    public String details() {
        return "目标角色展示一张手牌，然后若你弃置一张与所展示牌相同花色的手牌，你对其造成1点火焰伤害。";
    }
}
