package sanguosha.people.wei;

import sanguosha.cards.Card;
import sanguosha.cardsheap.CardsHeap;

import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

public class ZhenJi extends Person {
    public ZhenJi() {
        super(3, "female", Nation.WEI);
    }

    @Skill("洛神")
    @Override
    public void beginPhase() {
        if (launchSkill("洛神")) {
            Card c = CardsHeap.judge(this);
            while (c.isBlack()) {
                addCard(CardsHeap.getJudgeCard());
                if (launchSkill("洛神")) {
                    c = CardsHeap.judge(this);
                } else {
                    break;
                }
            }
        }
    }

    @Skill("倾国")
    @Override
    public boolean skillShan() {
        if (requestShan()) {
            if (launchSkill("倾国")) {
                return  requestRedBlack("black", true) != null;
            }
        }
        return false;
    }

    @Override
    public String name() {
        return "甄姬";
    }

    @Override
    public String skillsDescription() {
        return "倾国：你可以将一张黑色手牌当【闪】使用或打出。\n" +
                "洛神：准备阶段，你可以进行判定，若结果为黑色，你获得此牌，然后你可以重复此流程。";
    }
}
