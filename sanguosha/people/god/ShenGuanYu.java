package sanguosha.people.god;

import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.cards.basic.Sha;
import sanguosha.cards.basic.Tao;
import sanguosha.cards.strategy.TaoYuanJieYi;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.people.Person;
import sanguosha.skills.ForcesSkill;

import java.util.ArrayList;
import java.util.HashMap;

public class ShenGuanYu extends God {
    private HashMap<Person, Integer> mengYan = new HashMap<>();
    private boolean useHeartSha = false;

    public ShenGuanYu() {
        super(5, null);
    }

    @ForcesSkill("武魂")
    @Override
    public void addCard(Card c) {
        if (c.color() == Color.HEART) {
            Sha sha = new Sha(c.color(), c.number());
            sha.setThisCard(c);
            super.addCard(sha);
        }
        else {
            super.addCard(c);
        }
    }

    @Override
    public boolean useSha(Card card) {
        if (super.useSha(card) && card.color() == Color.HEART) {
            useHeartSha = true;
            return true;
        }
        return false;
    }

    @Override
    public int getShaDistance() {
        if (useHeartSha) {
            useHeartSha = false;
            return 10000;
        }
        return super.getShaDistance();
    }

    @Override
    public void gotHurt(ArrayList<Card> cards, Person p, int num) {
        println(p + " gets " + num + " 梦魇 marks");
        mengYan.putIfAbsent(p, 0);
        mengYan.put(p, mengYan.get(p) + num);
        println("now " + p + " has " + mengYan.get(p) + " 梦魇 marks");
    }

    @ForcesSkill("梦魇")
    @Override
    public void die() {
        super.die();
        int maxMarks = 0;
        ArrayList<Person> maxPerson = new ArrayList<>();
        for (Person p: mengYan.keySet()) {
            if (mengYan.get(p) == maxMarks) {
                maxPerson.add(p);
            }
            else if (mengYan.get(p) > maxMarks) {
                maxMarks = mengYan.get(p);
                maxPerson.clear();
                maxPerson.add(p);
            }
        }
        Person luckyGuy = maxPerson.get(0);
        if (maxPerson.size() > 1) {
            luckyGuy = chooseManyFromProvided(1, maxPerson).get(0);
        }
        println("lucky guy is " + luckyGuy + "!");
        Card c = CardsHeap.judge(luckyGuy);
        if (!(c instanceof Tao || c instanceof TaoYuanJieYi)) {
            luckyGuy.die();
        } else {
            println(luckyGuy + " is really lucky!");
        }
    }

    @Override
    public String toString() {
        return "神关羽";
    }
}
