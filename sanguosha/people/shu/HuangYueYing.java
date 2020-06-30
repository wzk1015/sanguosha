package sanguosha.people.shu;

import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.ForcesSkill;
import sanguosha.skills.Skill;

public class HuangYueYing extends Person {
    public HuangYueYing() {
        super(3, "female", Nation.SHU);
    }

    @Skill("急智")
    @Override
    public void useStrategy() {
        if (launchSkill("急智")) {
            drawCard();
        }
    }

    @ForcesSkill("奇才")
    @Override
    public boolean hasQiCai() {
        return true;
    }

    @Override
    public String name() {
        return "黄月英";
    }

    @Override
    public String skillsDescription() {
        return "集智：当你使用普通锦囊牌时，你可以摸一张牌。\n" +
                "奇才：锁定技，你使用锦囊牌无距离限制。";
    }
}
