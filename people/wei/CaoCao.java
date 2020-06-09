package people.wei;

import cards.Card;
import cards.basic.Sha;
import manager.GameManager;
import manager.IO;
import people.Nation;
import people.Person;
import skills.KingSkill;
import skills.Skill;

import java.util.ArrayList;

public class CaoCao extends Person {
    public CaoCao() {
        super(4, Nation.WEI);
    }

    @Skill("奸雄")
    @Override
    public void gotHurt(Card card, Person p, int num) {
        if (IO.launchSkill(this, "奸雄")) {
            IO.println(this + "uses 奸雄");
            if (card != null && card.isNotTaken()) {
                addCard(card);
                card.setTaken(true);
            }
        }
    }

    @KingSkill("护驾")
    @Override
    public boolean skillShan() {
        IO.println(this + "uses 护驾");
        ArrayList<Person> weiPeople = GameManager.peoplefromNation(Nation.WEI);
        weiPeople.remove(this);
        if (weiPeople.isEmpty()) {
            IO.println("no 魏 people available");
            return false;
        }
        for (Person p : weiPeople) {
            if (p.requestShan()) {
                IO.println(p + " answers 护驾 from " + this);
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "曹操";
    }
}
