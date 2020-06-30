package sanguosha.people.shu;

import sanguosha.cards.Card;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.manager.GameManager;

import sanguosha.manager.Utils;
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
            Utils.checkCardsNum();
            CardsHeap.setDrawCards(heap);
        }
    }

    @ForcesSkill("空城")
    @Override
    public boolean hasKongCheng() {
        return true;
    }

    @Override
    public String name() {
        return "诸葛亮";
    }

    @Override
    public String skillsDescription() {
        return "观星：准备阶段，你可以观看牌堆顶的X张牌（X为存活角色数且最多为5），然后以任意顺序放回牌堆顶或牌堆底。\n" +
                "空城：锁定技，若你没有手牌，你不能成为【杀】或【决斗】的目标。";
    }
}
