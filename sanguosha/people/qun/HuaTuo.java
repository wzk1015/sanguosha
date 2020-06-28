package sanguosha.people.qun;

import sanguosha.cards.Card;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

import java.util.ArrayList;

public class HuaTuo extends Person {

    public HuaTuo() {
        super(3, Nation.QUN);
    }

    @Skill("急救")
    @Override
    public boolean requestTao() {
        if (!isMyRound() && launchSkill("急救")) {
            if (chooseNoNull("hand card", "equipment").equals("hand card")) {
                if (requestRedBlack("red") != null) {
                    return true;
                }
                Card c = chooseCard(new ArrayList<>(getEquipments().values()));
                while (c != null && c.isBlack()) {
                    printlnToIO("you can't choose black card");
                    c = chooseCard(new ArrayList<>(getEquipments().values()));
                }
                if (c != null) {
                    loseCard(c);
                    return true;
                }
            }
        }
        return super.requestTao();
    }

    @Skill("青囊")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("青囊") && hasNotUsedSkill1()) {
            Person p = selectPlayer();
            if (p.getHP() == p.getMaxHP()) {
                printlnToIO("you can't choose person with maxHP");
                return true;
            }
            Card c = requestCard(null);
            if (c == null) {
                return true;
            }
            p.recover(1);
            setHasUsedSkill1(true);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "华佗";
    }
}
