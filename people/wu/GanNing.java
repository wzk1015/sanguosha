package people.wu;

import cards.Card;
import cards.strategy.GuoHeChaiQiao;
import cardsheap.CardsHeap;
import people.Nation;
import people.Person;
import skills.Skill;

public class GanNing extends Person {
    public GanNing() {
        super(4, Nation.WU);
    }

    @Skill("奇袭")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("奇袭")) {
            Card c = requestRedBlack("black");
            if (c == null) {
                return true;
            }
            GuoHeChaiQiao chai = new GuoHeChaiQiao(c.color(), c.number());
            if (chai.askTarget(this)) {
                useCard(chai);
            } else {
                CardsHeap.retrieve(c);
            }

            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "甘宁";
    }
}
