package sanguosha.people.forest;

import sanguosha.cards.Card;
import sanguosha.manager.GameManager;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.ForcesSkill;
import sanguosha.skills.Skill;

public class ZhuRong extends Person {
    public ZhuRong() {
        super(4, "female", Nation.SHU);
    }

    @ForcesSkill("巨象")
    @Override
    public boolean hasJuXiang() {
        println(this + " uses 巨象");
        return false;
    }

    @Skill("烈刃")
    @Override
    public void shaSuccess(Person p) {
        if (getCards().size() > 0 && p.getCards().size() > 0 && launchSkill("烈刃")) {
            if (GameManager.pinDian(this, p)) {
                if (!p.getCardsAndEquipments().isEmpty()) {
                    Card c = chooseTargetCards(p);
                    p.loseCard(c, false);
                    addCard(c);
                }
            }
        }
    }

    @Override
    public String name() {
        return "祝融";
    }

    @Override
    public String skillsDescription() {
        return "巨象：锁定技，【南蛮入侵】对你无效；当其他角色使用的【南蛮入侵】结算结束后，你获得之。\n" +
                "烈刃：当你使用【杀】对目标角色造成伤害后，你可以与其拼点，若你赢，你获得其一张牌。";
    }
}
