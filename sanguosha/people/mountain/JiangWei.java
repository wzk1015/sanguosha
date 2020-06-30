package sanguosha.people.mountain;

import sanguosha.cards.Card;
import sanguosha.cards.basic.Sha;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.manager.GameManager;
import sanguosha.manager.Utils;
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
            while (!GameManager.reachablePeople(p, p.getShaDistance()).contains(this)
                    || p.getCardsAndEquipments().isEmpty()) {
                printlnToIO("he can't reach you or he has no cards");
                p = selectPlayer();
            }
            Sha sha = p.requestSha();
            if (sha == null) {
                p.loseCard(chooseTargetCards(p));
                return true;
            }
            CardsHeap.retrieve(sha);
            sha.setTarget(this);
            sha.setSource(p);
            p.useCard(sha);
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

    @WakeUpSkill("志继")
    public void zhiJi() {
        println(this + " uses 志继");
        if (getHP() == getMaxHP() || chooseNoNull("recover 1 HP", "draw 2 cards")
                .equals("draw 2 cards")) {
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
    public String name() {
        return "姜维";
    }

    @Override
    public String skillsDescription() {
        return "挑衅：出牌阶段限一次，你可以选择一名攻击范围内含有你的角色，然后除非该角色对你使用一张【杀】，否则你弃置其一张牌。\n" +
                "志继：觉醒技，准备阶段，若你没有手牌，你回复1点体力或摸两张牌，然后减1点体力上限，获得“观星”。\n" +
                (hasWakenUp() ? "观星：准备阶段，你可以观看牌堆顶的X张牌（X为全场角色数且最多为5），然后以任意顺序放回牌堆顶或牌堆底。" : "");
    }
}
