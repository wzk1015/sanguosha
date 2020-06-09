package people.shu;

import manager.IO;
import people.Nation;
import people.Person;
import skills.Skill;

public class HuangZhong extends Person {
    public HuangZhong() {
        super(4, Nation.SHU);
    }

    @Skill("烈弓")
    @Override
    public boolean shaCanBeShan(Person target) {
        if (getHP() <= target.getCards().size() ||
                getShaDistance() >= target.getCards().size()) {
            if (IO.launchSkill(this, "烈弓")) {
                IO.println(this + " uses 烈弓");
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "黄忠";
    }
}
