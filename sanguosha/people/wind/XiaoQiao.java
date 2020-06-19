package sanguosha.people.wind;

import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.cards.basic.HurtType;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

import java.util.ArrayList;

public class XiaoQiao extends Person {
    public XiaoQiao() {
        super(3, "female", Nation.QUN);
    }

    @Skill("天香")
    @Override
    public int hurt(ArrayList<Card> card, Person source, int num, HurtType type) {
        if (launchSkill("天香")) {
            Person p = selectPlayer();
            Card c = requestColor(Color.SPADE);
            if (c != null) {
                int value = p.hurt(card, source, num, type);
                p.drawCards(p.getMaxHP() - p.getHP());
                return value;
            }
        }
        return super.hurt(card, source, num, type);
    }

    @Skill("红颜")
    @Override
    public boolean hasHongYan() {
        return true;
    }

    @Override
    public String toString() {
        return "小乔";
    }
}
