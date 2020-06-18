package people.fire;

import cards.Card;
import cards.strategy.HuoGong;
import cardsheap.CardsHeap;
import people.Nation;
import people.Person;
import skills.ForcesSkill;
import skills.Skill;

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
                CardsHeap.retrieve(c);
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
    public String toString() {
        return "卧龙诸葛亮";
    }
}
