package sanguosha.people.mountain;

import sanguosha.cards.Card;
import sanguosha.cards.basic.Sha;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.manager.GameManager;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.AfterWakeSkill;
import sanguosha.skills.Skill;
import sanguosha.skills.WakeUpSkill;

import java.util.ArrayList;

public class JiangWei extends Person {
    public JiangWei() {
        super(4, Nation.SHU);
    }

    @Skill("挑衅")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("挑衅")) {
            println(this + " uses 挑衅");
            Person p = selectPlayer();
            while (!GameManager.reachablePeople(p, p.getShaDistance()).contains(this)) {
                println("he can't reach you");
                p = selectPlayer();
            }
            Sha sha = p.requestSha();
            if (sha == null) {
                p.loseCard(chooseTargetCardsAndEquipments(p));
                return true;
            }
            CardsHeap.retrieve(sha);
            sha.setTarget(this);
            useCard(sha);
            if (sha.isNotTaken()) {
                throwCard(sha);
            } else {
                sha.setTaken(false);
            }
            return true;
        }
        return false;
    }

    @AfterWakeSkill("观星")
    public void guanXing() {
        int num = Math.min(GameManager.getNumPlayers(), 5);
        ArrayList<Card> heap = CardsHeap.getDrawCards(num);

        ArrayList<Card> view = new ArrayList<>(heap.subList(0, num));
        heap = new ArrayList<>(heap.subList(num, heap.size()));

        println("choose cards that you want to put on top, in your expected order");
        ArrayList<Card> top = chooseCards(0, view);
        if (top != null) {
            view.removeAll(top);
            heap.addAll(0, top);
        }
        if (!view.isEmpty()) {
            println("now arrange cards to put at bottom, in your expected order");
            ArrayList<Card> bottom = chooseCards(view.size(), view);
            heap.addAll(bottom);
        }
        GameManager.checkCardsNum();
        CardsHeap.setDrawCards(heap);
    }

    @WakeUpSkill("志继")
    public void zhiJi() {
        println(this + " uses 志继");
        if (getHP() == getMaxHP() || chooseFromProvided("recover 1 HP", "draw 2 sanguosha.cards")
                .equals("draw 2 sanguosha.cards")) {
            drawCards(2);
        } else {
            recover(1);
        }
        setMaxHP(getMaxHP() - 1);
        wakeUp();
    }

    @Override
    public void beginPhase() {
        if (!hasWakenUp() && getCards().isEmpty()) {
            zhiJi();
        }
        if (hasWakenUp() && launchSkill("观星")) {
            guanXing();
        }
    }

    @Override
    public String toString() {
        return "姜维";
    }
}
