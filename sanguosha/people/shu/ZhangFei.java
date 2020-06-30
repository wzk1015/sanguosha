package sanguosha.people.shu;

import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.ForcesSkill;

public class ZhangFei extends Person {
    public ZhangFei() {
        super(4, Nation.SHU);
        paoXiao();
    }

    @ForcesSkill("咆哮")
    public void paoXiao() {
        setMaxShaCount(10000);
    }

    @Override
    public String name() {
        return "张飞";
    }

    @Override
    public String skillsDescription() {
        return "咆哮：锁定技，你使用【杀】无次数限制。";
    }
}
