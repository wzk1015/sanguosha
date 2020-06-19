package sanguosha.people.qun;

import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.ForcesSkill;

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
