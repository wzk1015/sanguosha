package people.qun;

import people.Nation;
import people.Person;
import skills.Skill;

public class LvBu extends Person {
    public LvBu() {
        super(4, Nation.QUN);
    }

    @Skill("无双")
    @Override
    public boolean usesWuShuang() {
        return launchSkill("无双");
    }

    @Override
    public String toString() {
        return "吕布";
    }
}
