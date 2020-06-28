package sanguosha.people.shu;

import sanguosha.cards.Card;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.manager.GameManager;

import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.ForcesSkill;
import sanguosha.skills.Skill;

import java.util.ArrayList;

public class ZhuGeLiang extends Person {
    public ZhuGeLiang() {
        super(3, Nation.SHU);
    }

    @Skill("观星")
    @Override
    public void beginPhase() {
        if (launchSkill("观星")) {
            int num = Math.min(GameManager.getNumPlayers(), 5);
            ArrayList<Card> heap = CardsHeap.getDrawCards(num);

            ArrayList<Card> view = new ArrayList<>(heap.subList(0, num));
            heap = new ArrayList<>(heap.subList(num, heap.size()));

            printlnToIO("choose cards that you want to put on top, in your expected order");
            ArrayList<Card> top = chooseCards(0, view);
            if (top != null) {
                view.removeAll(top);
                heap.addAll(0, top);
            }
            if (!view.isEmpty()) {
                printlnToIO("now arrange cards to put at bottom, in your expected order");
                ArrayList<Card> bottom = chooseCards(view.size(), view);
                heap.addAll(bottom);
            }
            GameManager.checkCardsNum();
            CardsHeap.setDrawCards(heap);
        }
    }

    @ForcesSkill("空城")
    @Override
    public boolean hasKongCheng() {
        return true;
    }

    @Override
    public String toString() {
        return "诸葛亮";
    }
}
