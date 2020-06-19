package sanguosha.people.forest;

import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

public class SunJian extends Person {
    public SunJian() {
        super(4, Nation.WU);
    }

    @Skill("英魂")
    @Override
    public void beginPhase() {
        if (getHP() < getMaxHP() && launchSkill("英魂")) {
            int x = getMaxHP() - getHP();
            String op1 = "draw " + x + ", throw 1";
            String op2 = "draw 1, throw " + x;
            Person p = selectPlayer();
            if (p != null) {
                if (chooseFromProvided(op1, op2).equals(op1)) {
                    p.drawCards(x);
                    p.loseCard(p.chooseCards(1, p.getCardsAndEquipments()));
                }
                else {
                    p.drawCard();
                    int num = p.getCardsAndEquipments().size();
                    if (num <= x) {
                        p.loseCard(p.getCardsAndEquipments());
                    } else {
                        p.loseCard(p.chooseCards(x, p.getCardsAndEquipments()));
                    }
                }
            }
        }
    }

    @Override
    public String toString() {
        return "孙坚";
    }
}
