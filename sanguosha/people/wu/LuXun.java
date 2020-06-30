package sanguosha.people.wu;

import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.ForcesSkill;
import sanguosha.skills.Skill;

public class LuXun extends Person {
    public LuXun() {
        super(3, Nation.WU);
    }

    @ForcesSkill("谦逊")
    @Override
    public boolean hasQianXun() {
        return true;
    }

    @Skill("连营")
    @Override
    public void lostCard() {
        if (getCards().isEmpty() && launchSkill("连营")) {
            drawCard();
        }
    }

    @Override
    public String name() {
        return "陆逊";
    }

    @Override
    public String skillsDescription() {
        return "谦逊：锁定技，你不能成为【顺手牵羊】和【乐不思蜀】的目标。\n" +
                "连营：当你失去最后的手牌时，你可以摸一张牌。";
    }
}
