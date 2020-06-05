package cards.strategy;

import cards.Color;
import cards.Strategy;
import people.Person;

public class JieDaoShaRen extends Strategy {
    private Person target2;

    public JieDaoShaRen(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        //TODO
        return null;
    }

    @Override
    public String toString() {
        return "借刀杀人";
    }

    public void setTarget2(Person target2) {
        this.target2 = target2;
    }

    public Person getTarget2() {
        return target2;
    }
}
