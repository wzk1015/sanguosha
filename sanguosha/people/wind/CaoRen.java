package sanguosha.people.wind;

import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

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
    public String name() {
        return "曹仁";
    }

    @Override
    public String skillsDescription() {
        return "结束阶段，你可以翻面，若如此做，你摸四张牌，然后选择一项：1.弃置一张不为装备牌的牌；2.使用一张装备牌。";
    }
}
