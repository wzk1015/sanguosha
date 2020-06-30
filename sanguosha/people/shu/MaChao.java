package sanguosha.people.shu;

import sanguosha.cardsheap.CardsHeap;

import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.ForcesSkill;
import sanguosha.skills.Skill;

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
            return CardsHeap.judge(this).isBlack();
        }
        return true;
    }

    @Override
    public String name() {
        return "马超";
    }

    @Override
    public String skillsDescription() {
        return "马术：锁定技，你计算与其他角色的距离-1。\n" +
                "铁骑：当你使用【杀】指定目标后，你可以进行判定，若结果为红色，该角色不能使用【闪】。";
    }
}
