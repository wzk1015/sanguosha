package cards.strategy;

import cards.Color;
import cards.Strategy;
import people.Person;

public class TieSuoLianHuan extends Strategy {
    private Person target2;

    public TieSuoLianHuan(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        if (!gotWuXie()) {
            getTarget().link();
        }
        if (!gotWuXie()) {
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
}
