package people.wu;

import people.Nation;
import people.Person;
import skills.ForcesSkill;
import skills.Skill;

public class LuXun extends Person {
    public LuXun() {
        super(3, Nation.WU);
    }

    @ForcesSkill("谦逊")
    @Override
    public boolean hasQianXun() {
        return true;
    }

    @Skill("连营")
    @Override
    public void lostCard() {
        if (getCards().isEmpty() && launchSkill("连营")) {
            drawCard();
        }
    }

    @Override
    public String toString() {
        return "陆逊";
    }
}
