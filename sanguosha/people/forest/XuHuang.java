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
            Card c = requestRedBlack("black", true);
            if (c == null) {
                return true;
            }
            if (c instanceof Strategy) {
                printlnToIO("you should use black basic card or equipment");
                addCard(c, false);
                return true;
            }
            BingLiangCunDuan bing  = new BingLiangCunDuan(c.color(), c.number());
            bing.setOwner(this);
            bing.setThisCard(c);
            if (bing.askTarget(this)
                    && bing.getTarget().addJudgeCard((JudgeCard) bing)) {
                useCard(bing);
            } else {
                addCard(CardsHeap.retrieve(c), false);
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
    public String name() {
        return "徐晃";
    }

    @Override
    public String skillsDescription() {
        return "断粮：你可以将一张黑色基本牌或黑色装备牌当【兵粮寸断】使用；你可以对距离为2的角色使用【兵粮寸断】。";
    }
}
