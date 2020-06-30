package sanguosha.people.fire;

import sanguosha.cards.Card;
import sanguosha.cards.strategy.HuoGong;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.ForcesSkill;
import sanguosha.skills.Skill;

public class WoLong extends Person {
    private boolean validBaZhen = true;

    public WoLong() {
        super(3, Nation.SHU);
    }

    @Override
    public boolean hasBaZhen() {
        return false;
    }

    @ForcesSkill("八阵")
    @Override
    public boolean skillShan() {
        if (validBaZhen) {
            println(this + " uses 八阵");
            return CardsHeap.judge(this).isRed();
        }
        return false;
    }

    @Override
    public void setBaZhen(boolean bool) {
        validBaZhen = bool;
    }

    @Skill("火计")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("火计")) {
            println(this + " uses 火计");
            Card c = requestRedBlack("red");
            HuoGong hg = new HuoGong(c.color(), c.number());
            hg.setThisCard(c);
            if (hg.askTarget(this)) {
                useCard(hg);
            } else {
                addCard(CardsHeap.retrieve(c), false);
            }
        }
        return false;
    }

    @Skill("看破")
    @Override
    public boolean skillWuxie() {
        if (launchSkill("看破")) {
            return requestRedBlack("black") != null;
        }
        return false;
    }

    @Override
    public String name() {
        return "卧龙诸葛亮";
    }

    @Override
    public String skillsDescription() {
        return "八阵：锁定技，若你的装备区里没有防具牌，你视为装备着【八卦阵】。\n" +
                "火计：你可以将一张红色手牌当【火攻】使用。\n" +
                "看破：你可以将一张黑色手牌当【无懈可击】使用。";
    }
}
