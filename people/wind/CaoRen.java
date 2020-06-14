package people.wind;

import people.Nation;
import people.Person;
import skills.Skill;

public class CaoRen extends Person {
    public CaoRen() {
        super(4, Nation.WEI);
    }

    @Skill("据守")
    @Override
    public void endPhase() {
        if (launchSkill("据守")) {
            drawCards(3);
            turnover();
        }
    }

    @Override
    public String toString() {
        return "曹仁";
    }
}
