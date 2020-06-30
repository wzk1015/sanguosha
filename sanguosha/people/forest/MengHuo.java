package sanguosha.people.forest;

import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.manager.IO;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.ForcesSkill;
import sanguosha.skills.Skill;

import java.util.ArrayList;

public class MengHuo extends Person {
    public MengHuo() {
        super(4, Nation.SHU);
    }

    @ForcesSkill("祸首")
    @Override
    public boolean hasHuoShou() {
        println(this + " uses 祸首");
        return true;
    }

    @Skill("再起")
    @Override
    public void drawPhase() {
        if (launchSkill("再起")) {
            ArrayList<Card> cs = new ArrayList<>(CardsHeap.getDrawCards(getMaxHP() - getHP())
                    .subList(0, getMaxHP() - getHP()));
            CardsHeap.getDrawCards(0).removeAll(cs);
            println("再起 cards:");
            IO.printCardsPublic(cs);
            for (Card c: cs) {
                if (c.color() == Color.HEART) {
                    recover(1);
                    CardsHeap.discard(c);
                } else {
                    addCard(c);
                }
            }
        }

        else {
            super.drawPhase();
        }
    }

    @Override
    public String name() {
        return "孟获";
    }

    @Override
    public String skillsDescription() {
        return "祸首：锁定技，【南蛮入侵】对你无效；当其他角色使用【南蛮入侵】指定目标后，你代替其成为此牌造成的伤害的来源。\n" +
                "再起：摸牌阶段，若你已受伤，你可以改为亮出牌堆顶的X张牌（X为你已损失的体力值+1），" +
                "然后回复等同于其中红桃牌数量的体力，并获得其余的牌。";
    }
}
