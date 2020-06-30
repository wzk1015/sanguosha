package sanguosha.people.wind;

import sanguosha.people.Nation;
import sanguosha.people.Person;
import sanguosha.skills.Skill;

public class HuangZhong extends Person {
    public HuangZhong() {
        super(4, Nation.SHU);
    }

    @Skill("烈弓")
    @Override
    public boolean shaCanBeShan(Person target) {
        if (getHP() <= target.getCards().size() ||
                getShaDistance() >= target.getCards().size()) {
            return !launchSkill("烈弓");
        }
        return true;
    }

    @Override
    public String name() {
        return "黄忠";
    }

    @Override
    public String skillsDescription() {
        return "当你于出牌阶段内使用【杀】指定一个目标后，若该角色的手牌数不小于你的体力值或不大于你的攻击范围，则你可以令其不能使用【闪】响应此【杀】。";
    }
}
