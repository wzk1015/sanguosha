package people.wei;

import cards.Card;
import manager.GameManager;
import manager.IO;
import people.Nation;
import people.Person;
import skills.Skill;

public class XunYu extends Person {
    public XunYu() {
        super(3, Nation.WEI);
    }

    @Skill("驱虎")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("驱虎")) {
            IO.println(this + "uses" + order);
            Person p = GameManager.selectPlayer(this);
            if (p == null) {
                return false;
            }
            while (p.getCards().size() >= 1 || p.getHP() <= getHP()) {
                if (p.getCards().size() >= 1) {
                    IO.println("target has no hand cards");
                } else {
                    IO.println("target's HP > your HP");
                }
                p = GameManager.selectPlayer(this);
                if (p == null) {
                    return false;
                }
            }
            if (GameManager.pinDian(this, p)) {
                Person p2 = GameManager.selectPlayer(this);
                if (p2 == null) {
                    return false;
                }
                while (p2 == p || p2 == this ||
                        !GameManager.reachablePeople(p, p.getShaDistance()).contains(p2)) {
                    if (p2 == p || p2 == this) {
                        IO.println("you can't choose yourself or himself");
                    } else {
                        IO.println("can't reach this person");
                    }
                    p2 = GameManager.selectPlayer(this);
                    if (p2 == null) {
                        return false;
                    }
                }
                p2.hurt(null, p, 1);
            } else {
                hurt(null, p, 1);
            }
            return true;
        }
        return false;
    }

    @Skill("节命")
    @Override
    public void gotHurt(Card card, Person source, int num) {
        if (IO.launchSkill(this, "节命")) {
            IO.println(this + "uses 节命");
            Person p = GameManager.selectPlayer(this, true);
            if (p.getMaxHP() > p.getHP()) {
                p.drawCards(p.getMaxHP() - p.getHP());
            }

        }
    }

    @Override
    public String toString() {
        return "荀彧";
    }
}
