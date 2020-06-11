package people.wei;

import cards.Card;
import cardsheap.CardsHeap;

import people.Nation;
import people.Person;
import skills.Skill;

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
                return  requestRedBlack("black") != null;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "甄姬";
    }
}
