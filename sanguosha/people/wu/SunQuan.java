package sanguosha.people.wu;

import sanguosha.cards.Card;

import sanguosha.people.Identity;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.ForcesSkill;
import sanguosha.skills.KingSkill;
import sanguosha.skills.Skill;

import java.util.ArrayList;

public class SunQuan extends Person {
    public SunQuan() {
        super(4, Nation.WU);
    }

    @Skill("制衡")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("制衡") && hasNotUsedSkill1()) {
            println(this + " uses 制衡");
            ArrayList<Card> cs = chooseCards(0, getCards());
            if (!cs.isEmpty()) {
                loseCard(cs);
                drawCards(cs.size());
            }
            setHasUsedSkill1(true);
            return true;
        }
        return false;
    }

    @ForcesSkill("救援")
    @KingSkill("救援")
    @Override
    public void gotSavedBy(Person p) {
        if (getIdentity() == Identity.KING && p.getNation() == Nation.WU) {
            println(this + " uses 救援");
            recover(1);
        }
    }

    @Override
    public boolean requestTao() {
        return super.requestTao();
    }

    @Override
    public String toString() {
        return "孙权";
    }
}
