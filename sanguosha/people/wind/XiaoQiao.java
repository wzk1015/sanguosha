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
    public String name() {
        return "小乔";
    }

    @Override
    public String skillsDescription() {
        return "天香：当你受到伤害时，你可以弃置一张红桃手牌并选择一名其他角色。若如此做，你将此伤害转移给该角色，然后其摸X张牌（X为该角色已损失的体力值）。\n" +
                "红颜：锁定技，你的黑桃牌视为红桃牌。";
    }
}
