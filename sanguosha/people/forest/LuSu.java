package sanguosha.people.forest;

import sanguosha.cards.Card;
import sanguosha.manager.GameManager;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

import java.util.ArrayList;

public class LuSu extends Person {
    public LuSu() {
        super(3, Nation.WU);
    }

    @Skill("好施")
    @Override
    public void drawPhase() {
        if (launchSkill("好施")) {
            drawCards(4);
            if (getCards().size() > 5) {
                ArrayList<Person> minPeople = new ArrayList<>();
                int minNum = 10000;
                for (Person p: GameManager.getPlayers()) {
                    if (p.getCards().size() == minNum) {
                        minPeople.add(p);
                    }
                    else if (p.getCards().size() < minNum) {
                        minNum = p.getCards().size();
                        minPeople.clear();
                        minPeople.add(p);
                    }
                }
                Person minPerson = minPeople.get(0);
                if (minPeople.size() > 1) {
                    minPerson = chooseManyFromProvided(1, minPeople).get(0);
                }
                ArrayList<Card> cards = chooseCards(getCards().size() / 2, getCards());
                loseCard(cards, false);
                minPerson.addCard(cards);
            }
        }
        else {
            super.drawPhase();
        }
    }

    @Skill("缔盟")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("缔盟") && hasNotUsedSkill1()) {
            Person p1;
            Person p2;
            do {
                println("choose 2 players");
                p1 = selectPlayer();
                p2 = selectPlayer();
            } while (p1 != null && p2 != null && (p1 == p2 || getCards().size() <
                    Math.abs(p1.getCards().size() - p2.getCards().size())));
            if (p1 == null || p2 == null) {
                return true;
            }
            loseCard(chooseCards(Math.abs(p1.getCards().size()
                    - p2.getCards().size()), getCards()));
            final ArrayList<Card> c2 = new ArrayList<>(p2.getCards());
            final ArrayList<Card> c1 = new ArrayList<>(p1.getCards());
            p1.getCards().clear();
            p2.getCards().clear();
            p1.addCard(c2);
            p2.addCard(c1);
            setHasUsedSkill1(true);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "鲁肃";
    }
}
