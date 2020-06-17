package people.qun;

import people.Nation;
import people.Person;
import skills.ForcesSkill;

public class LvBu extends Person {
    public LvBu() {
        super(4, Nation.QUN);
    }

    @ForcesSkill("无双")
    @Override
    public boolean hasWuShuang() {
        return true;
    }

    @Override
    public String toString() {
        return "吕布";
    }
}
