package people.wei;

import cards.Card;
import manager.GameManager;
import manager.IO;
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
        if (IO.launchSkill(this, "突袭")) {
            IO.println(this + "uses 突袭");
            while (true) {
                Person p1 = GameManager.selectPlayer(this);
                Person p2 = GameManager.selectPlayer(this);
                if (p1 == null || p2 == null) {
                    break;
                }
                if (p1.getCards().isEmpty() || p2.getCards().isEmpty()) {
                    IO.println("target has no hand cards");
                    continue;
                }
                Card c1 = IO.chooseAnonymousCard(this, p1.getCards());
                Card c2 = IO.chooseAnonymousCard(this, p2.getCards());
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
