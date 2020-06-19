package sanguosha.people.forest;

import sanguosha.cards.basic.Sha;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.manager.GameManager;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.ForcesSkill;
import sanguosha.skills.RestrictedSkill;

import java.util.ArrayList;
import java.util.Iterator;

public class JiaXu extends Person {
    private boolean hasLuanWu;

    public JiaXu() {
        super(3, Nation.QUN);
    }

    @ForcesSkill("完杀")
    @Override
    public boolean hasWanSha() {
        println(this + " uses 完杀");
        return true;
    }

    public ArrayList<Person> nearestPerson(Person p) {
        if (GameManager.reachablePeople(p, getShaDistance()).isEmpty()) {
            return new ArrayList<>();
        }
        ArrayList<Person> nearby = new ArrayList<>();
        int minDistance = 100;
        for (Person p2 : GameManager.getPlayers()) {
            if (p2 == p) {
                continue;
            }
            if (GameManager.calDistance(p, p2) < minDistance) {
                nearby.clear();
                nearby.add(p2);
                minDistance = GameManager.calDistance(p, p2);
            }
            if (GameManager.calDistance(p, p2) == minDistance) {
                nearby.add(p2);
            }
        }
        nearby.removeIf(p3 -> p3.hasKongCheng() && p3.getCards().isEmpty());
        return nearby;
    }

    @RestrictedSkill("乱武")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("乱武") && !hasLuanWu) {
            Iterator<Person> it = GameManager.getPlayers().iterator();
            while (it.hasNext()) {
                Person p = it.next();
                if (p == this) {
                    continue;
                }
                println("乱武 towards " + p);
                ArrayList<Person> p2s = nearestPerson(p);
                if (p2s.isEmpty()) {
                    p.loseHP(1);
                    continue;
                }
                Person p2 = p2s.get(0);
                if (p2s.size() > 1) {
                    p2 = chooseManyFromProvided(1, p2s).get(0);
                }
                Sha sha = p.requestSha();
                if (sha == null) {
                    p.loseHP(1);
                    continue;
                }
                CardsHeap.retrieve(sha);
                sha.setTarget(p2);
                useCard(sha);
                if (sha.isNotTaken()) {
                    throwCard(sha);
                } else {
                    sha.setTaken(false);
                }
                if (p.isDead()) {
                    it.remove();
                }
            }
            hasLuanWu = true;
            return true;
        }
        return false;
    }

    @ForcesSkill("帷幕")
    @Override
    public boolean hasWeiMu() {
        println(this + " uses 帷幕");
        return true;
    }

    @Override
    public String toString() {
        return "贾诩";
    }
}
