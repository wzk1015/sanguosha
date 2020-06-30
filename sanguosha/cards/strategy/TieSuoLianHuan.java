package sanguosha.cards.strategy;

import sanguosha.cards.Color;
import sanguosha.cards.Strategy;
import sanguosha.people.Person;

public class TieSuoLianHuan extends Strategy {
    private Person target2;

    public TieSuoLianHuan(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        if (!gotWuXie(getTarget())) {
            getTarget().link();
        }
        if (!gotWuXie(getTarget2())) {
            getTarget2().link();
        }
        return true;
    }

    @Override
    public String toString() {
        return "铁索连环";
    }

    public Person getTarget2() {
        return target2;
    }

    public void setTarget2(Person target2) {
        this.target2 = target2;
    }

    @Override
    public boolean needChooseTarget() {
        return true;
    }

    @Override
    public boolean askTarget(Person user) {
        setSource(user);

        while (true) {
            user.printlnToIO("choose target 1");
            Person p1 = getSource().selectPlayer(true);
            user.printlnToIO("choose target 2");
            Person p2 = getSource().selectPlayer(true);
            if (p1 == null || p2 == null) {
                return false;
            }
            if (p1 == p2) {
                getSource().printlnToIO("can't select two same people");
                continue;
            }
            setTarget(p1);
            setTarget2(p2);
            return true;
        }
    }

    @Override
    public String details() {
        return "目标角色分别横置或重置其武将牌。当一名处于横置状态的角色受到属性伤害，即使其死亡，" +
                "也会令其它处于连环状态的角色受到同来源、同属性、同程度的伤害。经由连环传导的伤害不能再次被传导。" +
                "重铸：你可以从手里弃掉这张牌，然后摸一张牌。";
    }
}
