package sanguosha.people.wu;

import sanguosha.cards.Card;
import sanguosha.cards.strategy.GuoHeChaiQiao;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

public class GanNing extends Person {
    public GanNing() {
        super(4, Nation.WU);
    }

    @Skill("奇袭")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("奇袭")) {
            Card c = requestRedBlack("black", true);
            if (c == null) {
                return true;
            }
            GuoHeChaiQiao chai = new GuoHeChaiQiao(c.color(), c.number());
            if (chai.askTarget(this)) {
                useCard(chai);
            } else {
                addCard(CardsHeap.retrieve(c), false);
            }

            return true;
        }
        return false;
    }

    @Override
    public String name() {
        return "甘宁";
    }

    @Override
    public String skillsDescription() {
        return "奇袭：你可以将一张黑色牌当【过河拆桥】使用。";
    }
}
