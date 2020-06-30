package sanguosha.people.god;

import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.cards.basic.Sha;
import sanguosha.cards.basic.Tao;
import sanguosha.cards.strategy.TaoYuanJieYi;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.manager.IO;
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
    public void addCard(Card c, boolean print) {
        if (c.color() == Color.HEART) {
            Sha sha = new Sha(c.color(), c.number());
            sha.setThisCard(c);
            super.addCard(sha, print);
        }
        else {
            super.addCard(c, print);
        }
    }

    @Override
    public void loseCard(Card c, boolean throwAway, boolean print) {
        if (c.color() == Color.HEART && (getCards().contains(c)
                || getCards().contains(c.getThisCard().get(0)))) {
            getCards().remove(c);
            if (!isDead()) {
                lostCard();
            }
            if (print && throwAway) {
                print(this + " lost hand card: ");
                IO.printCardPublic(c.getThisCard().get(0));
            }
            c.getThisCard().get(0).setOwner(null);
            if (throwAway) {
                CardsHeap.discard(c.getThisCard().get(0));
            } else {
                c.getThisCard().get(0).setTaken(true);
            }
        }
        else {
            super.loseCard(c, throwAway, print);
        }
    }

    @Override
    public boolean useSha(Card card) {
        if (super.useSha(card)) {
            useHeartSha = card.color() == Color.HEART;
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
    public String getExtraInfo() {
        String ans = "梦魇 marks: ";
        for (Person p: mengYan.keySet()) {
            ans += p + mengYan.get(p).toString() + " ";
        }
        return ans;
    }

    @Override
    public String name() {
        return "神关羽";
    }

    @Override
    public String skillsDescription() {
        return "武神：锁定技，你的红桃手牌视为【杀】；你使用红桃【杀】无距离限制。\n" +
                "武魂：锁定技，当你受到1点伤害后，你令伤害来源获得1枚“梦魇”标记；" +
                "当你死亡时，你令拥有最多“梦魇”标记的一名其他角色判定，若结果不为【桃】或【桃园结义】，则该角色死亡。";
    }
}
