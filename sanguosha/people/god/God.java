package sanguosha.people.god;

import sanguosha.people.Nation;
import sanguosha.people.Person;

public abstract class God extends Person {
    public God(int maxHP, Nation nation) {
        super(maxHP, nation);
    }

    @Override
    public void initialize() {
        Nation nation = null;
        printlnToIO("you are GOD! select a nation");
        nation = chooseNoNull(Nation.WEI, Nation.SHU, Nation.WU, Nation.QUN);
        setNation(nation);
    }

    @Override
    public void run() {
        println("========GOD IS COMING!========");
        super.run();
    }
}
