package sanguosha.people.wu;

import sanguosha.cards.Card;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

import java.util.ArrayList;

public class SunShangXiang extends Person {
    public SunShangXiang() {
        super(3, "female", Nation.WU);
    }

    @Skill("枭姬")
    @Override
    public void lostEquipment() {
        if (launchSkill("枭姬")) {
            drawCards(2);
        }
    }

    @Skill("结姻")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (getCards().size() >= 2 && order.equals("结姻") && hasNotUsedSkill1()) {
            println(this + " uses 结姻");
            Person p = selectPlayer();
            if (p == null) {
                return true;
            }
            if (p.getSex().equals("female")) {
                printlnToIO("you can't choose female player");
                return true;
            }
            if (p.getHP() == p.getMaxHP()) {
                printlnToIO("you can't choose player with maxHP");
            }
            ArrayList<Card> cs = chooseCards(2, getCards());
            loseCard(cs);
            this.recover(1);
            p.recover(1);
            setHasUsedSkill1(true);
            return true;
        }
        return false;
    }

    @Override
    public String name() {
        return "孙尚香";
    }

    @Override
    public String skillsDescription() {
        return "结姻：出牌阶段限一次，你可以弃置两张手牌，令你和一名已受伤的男性角色各回复1点体力。\n" +
                "枭姬：当你失去装备区里的一张牌后，你可以摸两张牌。";
    }
}
