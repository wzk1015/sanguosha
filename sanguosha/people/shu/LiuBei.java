package sanguosha.people.shu;

import sanguosha.cards.Card;
import sanguosha.cards.basic.Sha;
import sanguosha.manager.GameManager;

import sanguosha.people.Identity;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.KingSkill;
import sanguosha.skills.Skill;

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
        Person p = selectPlayer();
        if (p == null) {
            return;
        }
        ArrayList<Card> cards = chooseManyFromProvided(0, getCards());
        if (cards == null || cards.isEmpty()) {
            return;
        }
        println(this + " gives " + cards.size() + " cards to " + p);
        p.addCard(cards);
        rendeCount += cards.size();
        if (!hasRecovered && rendeCount >= 2) {
            recover(1);
            hasRecovered = true;
        }
    }

    @KingSkill("激将")
    public Sha jiJiang() {
        ArrayList<Person> shuPeople = GameManager.peoplefromNation(Nation.SHU);
        shuPeople.remove(this);
        if (shuPeople.isEmpty()) {
            println("no 蜀 people available");
            return null;
        }
        for (Person p : shuPeople) {
            Sha sha = p.requestSha();
            if (sha != null) {
                println(p + " answers 激将 from " + this);
                return sha;
            }
        }
        return null;
    }

    @Override
    public boolean skillSha() {
        if (getIdentity() == Identity.KING && launchSkill("激将")) {
            return jiJiang() != null;
        }
        return false;
    }

    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("仁德")) {
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

    @Override
    public String name() {
        return "刘备";
    }

    @Override
    public String skillsDescription() {
        return "仁德：出牌阶段，你可以将任意张手牌交给其他角色，然后你于此阶段内给出第二张手牌时，你回复1点体力。\n" +
                "激将：主公技，其他蜀势力角色可以在你需要时代替你使用或打出【杀】。（视为由你使用或打出）。";
    }
}
