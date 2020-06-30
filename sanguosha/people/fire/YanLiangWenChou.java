package sanguosha.people.fire;

import sanguosha.cards.Card;
import sanguosha.cards.strategy.JueDou;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

public class YanLiangWenChou extends Person {
    private Card judgeCard;

    public YanLiangWenChou() {
        super(4, Nation.QUN);
    }

    @Override
    public void drawPhase() {
        if (launchSkill("双雄")) {
            Card c = CardsHeap.judge(this);
            addCard(CardsHeap.getJudgeCard());
            judgeCard = c;
            setHasUsedSkill1(true);
        }
        super.drawPhase();
    }

    @Skill("双雄")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("双雄") && !hasNotUsedSkill1()) {
            println(this + " uses 双雄");
            Card c = requestRedBlack(judgeCard.isRed() ? "red" : "black");
            JueDou jd = new JueDou(c.color(), c.number());
            jd.setThisCard(c);
            if (jd.askTarget(this)) {
                useCard(jd);
            } else {
                addCard(CardsHeap.retrieve(c), false);
            }
            return true;
        }
        return false;
    }

    @Override
    public String name() {
        return "颜良文丑";
    }

    @Override
    public String skillsDescription() {
        return "双雄：摸牌阶段，你可以改为进行判定，你获得生效后的判定牌，" +
                "然后本回合你可以将与判定结果颜色不同的一张手牌当【决斗】使用。";
    }
}
