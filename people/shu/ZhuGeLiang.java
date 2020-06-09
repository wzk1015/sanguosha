package people.shu;

import cards.Card;
import cardsheap.CardsHeap;
import manager.GameManager;
import manager.IO;
import manager.Utils;
import people.Nation;
import people.Person;
import skills.ForcesSkill;
import skills.Skill;

import java.util.ArrayList;

public class ZhuGeLiang extends Person {
    public ZhuGeLiang() {
        super(3, Nation.SHU);
    }

    @Skill("观星")
    @Override
    public void beginPhase() {
        if (IO.launchSkill(this,"观星")) {
            IO.println(this + " uses 观星");
            int num = Math.min(GameManager.getNumPlayers(), 5);
            ArrayList<Card> heap = CardsHeap.getDrawCards(num);
            final int originalSize = heap.size();

            ArrayList<Card> view = new ArrayList<>(heap.subList(0, num));
            heap = new ArrayList<>(heap.subList(num, heap.size()));

            IO.println("choose cards that you want to put on top, in your expected order");
            ArrayList<Card> top = IO.chooseCards(this, 0, view);
            if (top != null) {
                view.removeAll(heap);
                heap.addAll(0, top);
            }

            IO.println("now arrange cards to put at bottom, in your expected order");
            ArrayList<Card> bottom = IO.chooseCards(this, view.size(), view);
            heap.addAll(bottom);

            Utils.assertTrue(heap.size() == originalSize, "invalid cards heap size after 观星");
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
