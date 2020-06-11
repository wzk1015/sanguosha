package people.wu;

import people.Nation;
import people.Person;
import skills.Skill;

public class HuangGai extends Person {
    public HuangGai() {
        super(4, Nation.WU);
    }

    @Skill("苦肉")
    @Override
    public boolean useSkillInUsePhase(String order) {
        if (order.equals("苦肉")) {
            println(this + " uses 苦肉");
            loseHP(1);
            drawCards(2);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "黄盖";
    }
}
