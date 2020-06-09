package people.shu;

import cards.Card;
import cards.basic.Sha;
import manager.GameManager;
import manager.IO;
import people.Identity;
import people.Nation;
import people.Person;
import skills.KingSkill;
import skills.Skill;

import java.util.ArrayList;

public class LiuBei extends Person {
    private int rendeCount;
    private boolean hasRecovered;


    public LiuBei() {
        super(4, Nation.SHU);
    }

    @Override
    public void beginPhase() {
        rendeCount = 0;
        hasRecovered = false;
    }

    @Skill("仁德")
    public void renDe() {
        Person p = GameManager.selectPlayer(this);
        if (p == null) {
            return;
        }
        ArrayList<Card> cards = IO.chooseManyFromProvided(this, 0, getCards());
        if (cards == null || cards.isEmpty()) {
            return;
        }
        IO.println(this + " gives " + cards.size() + " cards to " + p);
        p.addCard(cards);
        rendeCount += cards.size();
        if (!hasRecovered && rendeCount >= 2) {
            recover(1);
            hasRecovered = true;
        }
    }

    @KingSkill("激将")
    public Sha jiJiang() {
        IO.println(this + "uses 激将");
        ArrayList<Person> shuPeople = GameManager.peoplefromNation(Nation.SHU);
        shuPeople.remove(this);
        if (shuPeople.isEmpty()) {
            IO.println("no 蜀 people available");
            return null;
        }
        for (Person p : shuPeople) {
            Sha sha = p.requestSha();
            if (sha != null) {
                IO.println(p + " answers 激将 from " + this);
                return sha;
            }
        }
        return null;
    }

    @Override
    public boolean skillSha() {
        if (IO.launchSkill(this, "激将")) {
            return jiJiang() != null;
        }
        return false;
    }

    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("仁德")) {
            IO.println(this + "uses 仁德");
            renDe();
            return true;
        }
        else if (order.equals("激将") && getIdentity() == Identity.KING) {
            Sha sha = jiJiang();
            useCard(sha);
            return true;
        }
        return false;
    }

    //TODO:激将

    @Override
    public String toString() {
        return "刘备";
    }
}
