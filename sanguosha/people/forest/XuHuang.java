package sanguosha.people.forest;

import sanguosha.cards.Card;
import sanguosha.cards.JudgeCard;
import sanguosha.cards.Strategy;
import sanguosha.cards.strategy.judgecards.BingLiangCunDuan;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

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
                printlnToIO("you should use black basic card or equipment");
                return true;
            }
            BingLiangCunDuan bing  = new BingLiangCunDuan(c.color(), c.number());
            bing.setOwner(this);
            bing.setThisCard(c);
            if (bing.askTarget(this)
                    && bing.getTarget().addJudgeCard((JudgeCard) bing)) {
                useCard(bing);
            } else {
                addCard(CardsHeap.retrieve(c));
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
