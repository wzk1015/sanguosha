package sanguosha.people.mountain;

import sanguosha.cards.Card;
import sanguosha.cardsheap.PeoplePool;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

import java.util.ArrayList;
import java.util.HashSet;

public class ZuoCi extends Person {
    private ArrayList<Person> huaShen = new ArrayList<>();
    private Person selected = null;

    public ZuoCi() {
        super(3, Nation.QUN);
    }

    @Override
    public void initialize() {
        huaShen.add(PeoplePool.allocOnePerson());
        println(this + " got 化身 " + huaShen.get(0));
        huaShen.add(PeoplePool.allocOnePerson());
        println(this + " got 化身 " + huaShen.get(1));
        selected = huaShen.get(0);
        huaShen();
    }

    @Skill("化身")
    public void huaShen() {
        if (launchSkill("化身")) {
            Person choice = selectPlayer(huaShen);
            if (choice != null) {
                selected = choice;
                setSex(selected.getSex());
                setNation(selected.getNation());
            }
        }
        printlnToIO("you can't use 化身 because I don't want to implement it");
    }

    public HashSet<String> getSkills() {
        HashSet<String> ans = new HashSet<>();
        ans.add("化身");
        ans.add("新生");
        if (selected != null) {
            ans.addAll(selected.getSkills());
        }
        return ans;
    }

    @Override
    public void beginPhase() {
        huaShen();
    }

    @Override
    public void endPhase() {
        huaShen();
    }

    @Skill("新生")
    @Override
    public void gotHurt(ArrayList<Card> cards, Person p, int num) {
        if (launchSkill("新生")) {
            huaShen.add(PeoplePool.allocOnePerson());
            println(this + " got new 化身" + huaShen.get(huaShen.size() - 1));
            println(this + " now has " + huaShen.size() + " 化身");
        }
    }

    @Override
    public String toString() {
        return "左慈";
    }
}
