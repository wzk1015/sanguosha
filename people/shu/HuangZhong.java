package people.shu;

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
            return !launchSkill("烈弓");
        }
        return true;
    }

    @Override
    public String toString() {
        return "黄忠";
    }
}
