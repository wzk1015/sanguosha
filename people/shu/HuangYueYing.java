package people.shu;

import people.Nation;
import people.Person;
import skills.ForcesSkill;
import skills.Skill;

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
    public String toString() {
        return "黄月英";
    }
}
