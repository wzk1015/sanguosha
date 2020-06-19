package sanguosha.people.forest;

import sanguosha.cards.Card;
import sanguosha.manager.GameManager;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.ForcesSkill;
import sanguosha.skills.Skill;

public class ZhuRong extends Person {
    public ZhuRong() {
        super(4, "female", Nation.SHU);
    }

    @ForcesSkill("巨象")
    @Override
    public boolean hasJuXiang() {
        println(this + " uses 巨象");
        return false;
    }

    @Skill("烈刃")
    @Override
    public void shaSuccess(Person p) {
        if (getCards().size() > 0 && p.getCards().size() > 0 && launchSkill("烈刃")) {
            if (GameManager.pinDian(this, p)) {
                if (!p.getCardsAndEquipments().isEmpty()) {
                    Card c = chooseTargetCardsAndEquipments(p);
                    p.loseCard(c, false);
                    addCard(c);
                }
            }
        }
    }

    @Override
    public String toString() {
        return "祝融";
    }
}
