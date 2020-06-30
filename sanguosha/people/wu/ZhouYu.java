package sanguosha.people.wu;

import sanguosha.cards.Card;
import sanguosha.cards.Color;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

public class ZhouYu extends Person {
    public ZhouYu() {
        super(3, Nation.WU);
    }

    @Skill("英姿")
    @Override
    public void drawPhase() {
        if (launchSkill("英姿")) {
            drawCards(3);
        }
        else {
            super.drawPhase();
        }
    }

    @Skill("反间")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (getCards().size() > 0 && order.equals("反间") && hasNotUsedSkill1()) {
            println(this + " uses 反间");
            Card c = chooseCard(getCards(), false);
            Person p = selectPlayer();
            if (p == null) {
                return true;
            }
            setHasUsedSkill1(true);
            Color clr = p.chooseNoNull(Color.SPADE, Color.CLUB, Color.HEART, Color.DIAMOND);
            p.addCard(c);
            if (c.color() != clr) {
                p.hurt((Card) null, this, 1);
            }
            return true;
        }
        return false;
    }

    @Override
    public String name() {
        return "周瑜";
    }

    @Override
    public String skillsDescription() {
        return "英姿：摸牌阶段，你可以多摸一张牌。\n" +
                "反间：出牌阶段限一次，你可以令一名其他角色选择一种花色，然后该角色获得你的一张手牌并展示之，若此牌的花色与其所选的花色不同，则你对其造成1点伤害。";
    }
}
