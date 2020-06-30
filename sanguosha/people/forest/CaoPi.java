package sanguosha.people.forest;

import sanguosha.cards.Card;
import sanguosha.cardsheap.CardsHeap;
import sanguosha.people.Identity;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.KingSkill;
import sanguosha.skills.Skill;

import java.util.ArrayList;

public class CaoPi extends Person {
    public CaoPi() {
        super(3, Nation.WEI);
    }

    @Skill("行殇")
    @Override
    public boolean usesXingShang() {
        return launchSkill("行殇");
    }

    @Skill("放逐")
    @Override
    public void gotHurt(ArrayList<Card> cards, Person p, int num) {
        if (launchSkill("放逐")) {
            Person target = selectPlayer();
            if (target != null) {
                target.drawCards(getMaxHP() - getHP());
                target.turnover();
            }
        }
    }

    @KingSkill("颂威")
    @Override
    public void otherPersonGetJudge(Person p) {
        if (getIdentity() == Identity.KING && CardsHeap.getJudgeCard().isBlack()
                && p.getNation() == Nation.WEI && p.launchSkill("颂威")) {
            drawCard();
        }
    }

    @Override
    public String name() {
        return "曹丕";
    }

    @Override
    public String skillsDescription() {
        return "行殇：当其他角色死亡时，你可以获得其所有牌。\n" +
                "放逐：当你受到伤害后，你可以令一名其他角色翻面，然后该角色摸X张牌（X为你已损失的体力值）。\n" +
                "颂威：主公技，当其他魏势力角色的黑色判定牌生效后，其可以令你摸一张牌。";
    }
}
