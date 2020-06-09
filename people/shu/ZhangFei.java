package people.shu;

import people.Nation;
import people.Person;
import skills.Skill;

public class ZhangFei extends Person {
    public ZhangFei() {
        super(4, Nation.SHU);
        paoXiao();
    }

    @Skill("咆哮")
    public void paoXiao() {
        setMaxShaCount(10000);
    }

    @Override
    public String toString() {
        return "张飞";
    }
}
