package sanguosha.people.wu;

import sanguosha.cards.Card;

import sanguosha.people.Identity;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.ForcesSkill;
import sanguosha.skills.KingSkill;
import sanguosha.skills.Skill;

import java.util.ArrayList;

public class SunQuan extends Person {
    public SunQuan() {
        super(4, Nation.WU);
    }

    @Skill("制衡")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("制衡") && hasNotUsedSkill1()) {
            println(this + " uses 制衡");
            ArrayList<Card> cs = chooseCards(0, getCardsAndEquipments());
            if (!cs.isEmpty()) {
                loseCard(cs);
                drawCards(cs.size());
            }
            setHasUsedSkill1(true);
            return true;
        }
        return false;
    }

    @ForcesSkill("救援")
    @KingSkill("救援")
    @Override
    public void gotSavedBy(Person p) {
        if (getIdentity() == Identity.KING && p.getNation() == Nation.WU) {
            println(this + " uses 救援");
            recover(1);
        }
    }

    @Override
    public boolean requestTao() {
        return super.requestTao();
    }

    @Override
    public String name() {
        return "孙权";
    }

    @Override
    public String skillsDescription() {
        return "制衡：出牌阶段限一次，你可以弃置任意张牌，然后摸等量的牌。\n" +
                "救援：主公技，锁定技，其他吴势力角色对你使用【桃】回复的体力+1。";
    }
}
