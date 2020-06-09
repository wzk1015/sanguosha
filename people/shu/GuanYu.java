package people.shu;

import cards.Card;
import cards.basic.Sha;
import manager.IO;
import people.Nation;
import people.Person;
import skills.Skill;

public class GuanYu extends Person {
    public GuanYu() {
        super(4, Nation.SHU);
    }

    @Skill("武圣")
    public Card wuSheng() {
        Card c = IO.requestRedBlack(this, "red");
        if (c != null) {
            IO.println(this + " uses 武圣");
            return c;
        }
        return null;
    }

    @Override
    public boolean skillSha() {
        return wuSheng() != null;
    }

    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("武圣")) {
            Card c = wuSheng();
            if (wuSheng() != null) {
                Sha sha = new Sha(c.color(), c.number());
                sha.setThisCard(c);
                useCard(sha);
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
