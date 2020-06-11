package people.wu;

import cards.Card;
import cards.strategy.GuoHeChaiQiao;
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
            Card c = chooseCard(getCards());
            if (c != null) {
                loseCard(c);
                new GuoHeChaiQiao(c.color(), c.number()).use();
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return null;
    }
}
