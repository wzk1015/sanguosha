package sanguosha.people.shu;

import sanguosha.cards.Card;
import sanguosha.cards.basic.Sha;

import sanguosha.cardsheap.CardsHeap;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

public class GuanYu extends Person {
    public GuanYu() {
        super(4, Nation.SHU);
    }

    @Skill("武圣")
    public Card wuSheng() {
        return requestRedBlack("red");
    }

    @Override
    public boolean skillSha() {
        if (launchSkill("武圣")) {
            return wuSheng() != null;
        }
        return false;
    }

    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("武圣")) {
            Card c = wuSheng();
            if (c == null) {
                return true;
            }
            Sha sha = new Sha(c.color(), c.number());
            sha.setThisCard(c);
            if (sha.askTarget(this)) {
                useCard(sha);
            } else {
                addCard(CardsHeap.retrieve(c));
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "关羽";
    }
}
