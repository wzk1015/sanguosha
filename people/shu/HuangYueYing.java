package people.shu;

import manager.IO;
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
        if (IO.launchSkill(this, "急智")) {
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
