package people.shu;

import cardsheap.CardsHeap;
import manager.IO;
import people.Nation;
import people.Person;
import skills.ForcesSkill;
import skills.Skill;

public class MaChao extends Person {
    public MaChao() {
        super(4, Nation.SHU);
    }

    @ForcesSkill("马术")
    @Override
    public boolean hasMaShu() {
        IO.println(this + " uses 马术");
        return true;
    }

    @Skill("铁骑")
    @Override
    public boolean shaCanBeShan(Person p) {
        if (IO.launchSkill(this, "铁骑")) {
            IO.println(this + " uses 铁骑");
            return CardsHeap.judge(this).isRed();
        }
        return false;
    }

    @Override
    public String toString() {
        return "马超";
    }
}
