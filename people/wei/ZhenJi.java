package people.wei;

import cards.Card;
import cardsheap.CardsHeap;
import manager.IO;
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
        if (IO.launchSkill(this, "洛神")) {
            IO.println(this + " uses 洛神");
            Card c = CardsHeap.judge(this);
            while (c.isBlack()) {
                addCard(CardsHeap.getJudgeCard());
                if (IO.launchSkill(this, "洛神")) {
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
            if (IO.launchSkill(this, "倾国")) {
                if (IO.requestRedBlack(this, "black") != null) {
                    IO.println(this + "uses 倾国");
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "甄姬";
    }
}
