package sanguosha.people.mountain;

import sanguosha.people.Nation;
import sanguosha.people.Person;

public class ZuoCi extends Person {

    public ZuoCi() {
        super(3, Nation.QUN);
    }

    @Override
    public void initialize() {
        zuoCiInitialize();
        //after this, ZuoCi switched to another person with isZuoCi == true
    }

    @Override
    public String name() {
        return "左慈";
    }

    @Override
    public String skillsDescription() {
        return "化身：游戏开始时，你随机获得两张武将牌作为\"化身\"牌，然后亮出其中一张，获得该\"化身\"牌的一个技能。" +
                "回合开始时或结束后，你可以更改亮出的\"化身\"牌。\n" +
                "新生：当你受到1点伤害后，你可以获得一张新的\"化身\"牌。";
    }
}
