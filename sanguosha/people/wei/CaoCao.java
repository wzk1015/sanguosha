package sanguosha.people.wei;

import sanguosha.cards.Card;
import sanguosha.manager.GameManager;

import sanguosha.people.Identity;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.KingSkill;
import sanguosha.skills.Skill;

import java.util.ArrayList;

public class CaoCao extends Person {
    public CaoCao() {
        super(4, Nation.WEI);
    }

    @Skill("奸雄")
    @Override
    public void gotHurt(ArrayList<Card> cs, Person p, int num) {
        if (launchSkill("奸雄")) {
            if (cs != null) {
                for (Card c: cs) {
                    if (c.isNotTaken()) {
                        addCard(c);
                        c.setTaken(true);
                    }
                }
            }
        }
    }

    @KingSkill("护驾")
    @Override
    public boolean skillShan() {
        if (getIdentity() == Identity.KING && launchSkill("护驾")) {
            ArrayList<Person> weiPeople = GameManager.peoplefromNation(Nation.WEI);
            weiPeople.remove(this);
            if (weiPeople.isEmpty()) {
                println("no 魏 people available");
                return false;
            }
            for (Person p : weiPeople) {
                if (p.requestShan()) {
                    println(p + " answers 护驾 from " + this);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String name() {
        return "曹操";
    }

    @Override
    public String skillsDescription() {
        return "奸雄：当你受到伤害后，你可以获得造成此伤害的牌。\n" +
                "护驾：主公技，当你需要使用或打出“闪”时，你可以令其他魏势力角色选择是否打出一张“闪”（视为由你使用或打出）。";
    }
}
