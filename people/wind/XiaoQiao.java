package people.wind;

import cards.Card;
import cards.Color;
import cards.basic.HurtType;
import manager.GameManager;
import people.Nation;
import people.Person;
import skills.Skill;

public class XiaoQiao extends Person {
    public XiaoQiao() {
        super(3, "female", Nation.QUN);
    }

    @Skill("天香")
    @Override
    public int hurt(Card card, Person source, int num, HurtType type) {
        if (launchSkill("天香")) {
            Person p = GameManager.selectPlayer(this);
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
