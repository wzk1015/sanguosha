package people.wu;

import cards.Card;

import people.Identity;
import people.Nation;
import people.Person;
import skills.ForcesSkill;
import skills.KingSkill;
import skills.Skill;

import java.util.ArrayList;

public class SunQuan extends Person {
    public SunQuan() {
        super(4, Nation.WU);
    }

    @Override
    public void beginPhase() {
        setHasUsedSkill1(false);
    }

    @Skill("制衡")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (launchSkill("制衡") && !hasUsedSkill1()) {
            ArrayList<Card> cs = chooseCards(0, getCards());
            if (!cs.isEmpty()) {
                throwCard(cs);
                drawCards(cs.size());
            }
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
