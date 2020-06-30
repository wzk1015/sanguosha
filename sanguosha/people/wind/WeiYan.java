package sanguosha.people.wind;

import sanguosha.manager.GameManager;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.ForcesSkill;

public class WeiYan extends Person {
    public WeiYan() {
        super(4, Nation.SHU);
    }

    @ForcesSkill("狂骨")
    @Override
    public void hurtOther(Person p, int num) {
        if (GameManager.calDistance(this, p) <= 1) {
            println(this + " uses 狂骨");
            recover(num);
        }
    }

    @Override
    public String name() {
        return "魏延";
    }

    @Override
    public String skillsDescription() {
        return "狂骨：锁定技，当你对距离1以内的一名角色造成1点伤害后，你回复1点体力。";
    }
}
