package people.wei;

import manager.IO;
import people.Person;
import skills.Skill;

public class GuoJia extends Person {
    public GuoJia(int maxHP) {
        super(maxHP);
    }

    @Skill("遗计")
    @Override
    public void gotHurt(Person source, int num) {
        if (IO.launchSkill(this, "遗计")) {
            drawCards(num * 2);
        }
    }

    @Override
    public String toString() {
        return "郭嘉";
    }
}
