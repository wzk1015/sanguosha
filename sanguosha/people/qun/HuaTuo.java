package sanguosha.people.qun;

import sanguosha.cards.Card;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

public class HuaTuo extends Person {

    public HuaTuo() {
        super(3, Nation.QUN);
    }

    @Skill("急救")
    @Override
    public boolean requestTao() {
        if (!isMyRound() && launchSkill("急救")) {
            return requestRedBlack("red", true) != null;
        }
        return super.requestTao();
    }

    @Skill("青囊")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("青囊") && hasNotUsedSkill1()) {
            Person p = selectPlayer();
            if (p.getHP() == p.getMaxHP()) {
                printlnToIO("you can't choose person with maxHP");
                return true;
            }
            Card c = requestCard(null);
            if (c == null) {
                return true;
            }
            p.recover(1);
            setHasUsedSkill1(true);
            return true;
        }
        return false;
    }

    @Override
    public String name() {
        return "华佗";
    }

    @Override
    public String skillsDescription() {
        return "急救：你的回合外，你可以将一张红色牌当【桃】使用。\n" +
                "青囊：出牌阶段限一次，你可以弃置一张手牌令一名角色回复1点体力。";
    }
}
