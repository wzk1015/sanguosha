package sanguosha.people;

import sanguosha.cards.Card;
import sanguosha.cards.Equipment;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.cardsheap.PeoplePool;
import sanguosha.manager.GameManager;
import sanguosha.skills.Skill;

import java.util.ArrayList;

public interface HuaShen extends PlayerIO, SkillLauncher {
    boolean isZuoCi();

    void setZuoCi(boolean zuoCi);

    void addHuaShen(Person p);

    ArrayList<Person> getHuaShen();

    void selectHuaShen(Person p);

    int getShaCount();

    ArrayList<Card> getExtraCards();

    void loseCard(ArrayList<Card> cs, boolean throwaway, boolean print);

    @Override
    boolean hasWuShuang();

    default void zuoCiInitialize() {
        setZuoCi(true);
        Person p1 = PeoplePool.allocOnePerson();
        addHuaShen(p1);
        println(this + " got 化身 " + p1);
        p1.setZuoCi(true);
        Person p2 = PeoplePool.allocOnePerson();
        addHuaShen(p2);
        println(this + " got 化身 " + p2);
        p2.setZuoCi(true);

        selectHuaShen(p1);
        while (!huaShen(false)) {
            printlnToIO("you must choose a 化身");
        }
    }

    @Skill("化身")
    default boolean huaShen(boolean beginPhase) {
        if (isZuoCi() && launchSkill("化身")) {
            Person choice = selectPlayer(getHuaShen(), true);
            if (choice != null) {
                println(this + " selects 化身 " + choice);
                selectHuaShen(choice);
                changePerson(choice);
                if (beginPhase) {
                    choice.run();
                }
                return true;
            }
        }
        //printlnToIO("you can't use 化身 because I don't want to implement it");
        return false;
    }

    @Skill("新生")
    default void xinSheng() {
        Person p = PeoplePool.allocOnePerson();
        addHuaShen(p);
        p.setZuoCi(true);
        println(this + " got new 化身 " + p);
        println(this + " now has " + getHuaShen().size() + " 化身");
    }

    default void changePerson(Person p) {
        p.setCurrentHP(getHP());
        p.setDaWu(isDaWu());
        p.setKuangFeng(isKuangFeng());
        p.setIdentity(getIdentity());
        p.setDrunk(isDrunk());
        p.setShaCount(getShaCount());
        if (isLinked() != p.isLinked()) {
            p.link();
        }
        if (isTurnedOver() != p.isTurnedOver()) {
            p.turnover();
        }
        p.getCards().clear();
        p.getCards().addAll(getCards());
        for (Equipment equipment : getEquipments().values()) {
            p.getEquipments().put(equipment.getEquipType(), equipment);
        }
        p.getJudgeCards().clear();
        p.getJudgeCards().addAll(getJudgeCards());
        if (getExtraCards() != null) {
            CardsHeap.discard(getExtraCards());
        }
        loseCard(p.getCards(), false, false);
        loseCard(new ArrayList<>(p.getRealJudgeCards()), false, false);
        loseCard(new ArrayList<>(p.getEquipments().values()), false, false);
        p.setHuaShen(getHuaShen());
        p.selectHuaShen(p);
        p.setZuoCi(isZuoCi());
        int index = GameManager.getPlayers().indexOf((Person) this);
        GameManager.getPlayers().set(index, p);
    }
}
