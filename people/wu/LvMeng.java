package people.wu;

import people.Nation;
import people.Person;
import skills.Skill;

public class LvMeng extends Person {
    private boolean hasSha = false;

    public LvMeng() {
        super(4, Nation.WU);
    }

    @Override
    public void beginPhase() {
        hasSha = false;
    }

    @Override
    public void shaBegin() {
        hasSha = true;
    }

    @Skill("克己")
    @Override
    public void throwPhase() {
        if (!hasSha && launchSkill("克己")) {
            return;
        }
        super.throwPhase();
    }

    @Override
    public String toString() {
        return "吕蒙";
    }
}
