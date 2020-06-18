package people.forest;

import cards.Card;
import cards.JudgeCard;
import cards.Strategy;
import cards.strategy.judgecards.BingLiangCunDuan;
import cardsheap.CardsHeap;
import people.Nation;
import people.Person;
import skills.Skill;

public class XuHuang extends Person {
    public XuHuang() {
        super(4, Nation.WEI);
    }

    @Skill("断粮")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("断粮")) {
            println(this + " uses 断粮");
            Card c = requestRedBlack("black");
            if (c == null) {
                return true;
            }
            if (c instanceof Strategy) {
                println("you should use black basic card or equipment");
                return true;
            }
            BingLiangCunDuan bing  = new BingLiangCunDuan(c.color(), c.number());
            bing.setOwner(this);
            bing.setThisCard(c);
            if (bing.askTarget(this)
                    && bing.getTarget().addJudgeCard((JudgeCard) bing)) {
                useCard(bing);
            } else {
                CardsHeap.retrieve(c);
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean hasDuanLiang() {
        return true;
    }

    @Override
    public String toString() {
        return "徐晃";
    }
}
