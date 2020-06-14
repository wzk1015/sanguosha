package people.wind;

import manager.GameManager;
import people.Nation;
import people.Person;
import skills.ForcesSkill;

public class WeiYan extends Person {
    public WeiYan() {
        super(4, Nation.SHU);
    }

    @ForcesSkill("狂骨")
    @Override
    public void hurtOther(Person p) {
        if (GameManager.calDistance(this, p) <= 1) {
            println(this + " uses 狂骨");
            recover(1);
        }
    }

    @Override
    public String toString() {
        return "魏延";
    }
}
