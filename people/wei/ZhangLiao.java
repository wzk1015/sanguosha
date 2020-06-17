package people.wei;

import cards.Card;

import people.Nation;
import people.Person;
import skills.Skill;

public class ZhangLiao extends Person {
    public ZhangLiao() {
        super(4, Nation.WEI);
    }

    @Skill("突袭")
    @Override
    public void drawPhase() {
        if (launchSkill("突袭")) {
            while (true) {
                Person p1 = selectPlayer();
                Person p2 = selectPlayer();
                if (p1 == null || p2 == null) {
                    break;
                }
                if (p1 == p2) {
                    println("can't select same person");
                    continue;
                }
                if (p1.getCards().isEmpty() || p2.getCards().isEmpty()) {
                    println("target has no hand cards");
                    continue;
                }
                Card c1 = chooseAnonymousCard(p1.getCards());
                Card c2 = chooseAnonymousCard(p2.getCards());
                p1.loseCard(c1, false);
                p2.loseCard(c2, false);
                addCard(c1);
                addCard(c2);
                return;
            }
        }
        super.drawPhase();
    }

    @Override
    public String toString() {
        return "张辽";
    }
}
