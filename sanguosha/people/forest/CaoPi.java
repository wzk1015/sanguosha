package sanguosha.people.forest;

import sanguosha.cards.Card;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.people.Identity;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.KingSkill;
import sanguosha.skills.Skill;

import java.util.ArrayList;

public class CaoPi extends Person {
    public CaoPi() {
        super(3, Nation.WEI);
    }

    @Skill("行殇")
    @Override
    public boolean usesXingShang() {
        return launchSkill("行殇");
    }

    @Skill("放逐")
    @Override
    public void gotHurt(ArrayList<Card> cards, Person p, int num) {
        if (launchSkill("放逐")) {
            Person target = selectPlayer();
            if (target != null) {
                target.drawCards(getMaxHP() - getHP());
                target.turnover();
            }
        }
    }

    @KingSkill("颂威")
    @Override
    public void otherPersonGetJudge(Person p) {
        if (getIdentity() == Identity.KING && CardsHeap.getJudgeCard().isBlack()
                && p.getNation() == Nation.WEI && p.launchSkill("颂威")) {
            drawCard();
        }
    }

    @Override
    public String toString() {
        return "曹丕";
    }
}
