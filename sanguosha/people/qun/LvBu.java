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
    public String name() {
        return "吕布";
    }

    @Override
    public String skillsDescription() {
        return "无双：锁定技，你使用的【杀】需两张【闪】才能抵消；与你进行【决斗】的角色每次需打出两张【杀】。";
    }
}
