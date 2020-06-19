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
    public String toString() {
        return "魏延";
    }
}
