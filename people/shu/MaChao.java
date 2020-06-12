package people.shu;

import cardsheap.CardsHeap;

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
        println(this + " uses 马术");
        return true;
    }

    @Skill("铁骑")
    @Override
    public boolean shaCanBeShan(Person p) {
        if (launchSkill("铁骑")) {
            return CardsHeap.judge(this).isRed();
        }
        return true;
    }

    @Override
    public String toString() {
        return "马超";
    }
}
