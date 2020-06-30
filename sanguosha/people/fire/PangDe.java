package sanguosha.people.fire;

import sanguosha.cards.Card;
import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.ForcesSkill;
import sanguosha.skills.Skill;

public class PangDe extends Person {
    public PangDe() {
        super(4, Nation.QUN);
    }

    @ForcesSkill("马术")
    @Override
    public boolean hasMaShu() {
        return true;
    }

    @Skill("猛进")
    @Override
    public void shaGotShan(Person p) {
        if (!p.getCardsAndEquipments().isEmpty() && launchSkill("猛进")) {
            Card c = chooseTargetCards(p);
            p.loseCard(c);
        }
    }

    @Override
    public String name() {
        return "庞德";
    }

    @Override
    public String skillsDescription() {
        return "马术：锁定技，你计算与其他角色的距离-1。\n" +
                "猛进：当你使用的【杀】被目标角色的【闪】抵消时，你可以弃置其一张牌。";
    }
}
