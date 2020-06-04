package cards.basic;

import cards.BasicCard;
import cards.Color;

public class Jiu extends BasicCard {
    public Jiu(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        if (getTarget().getHP() <= 0) {
            getTarget().recover(1);
        }

        else {
            getTarget().setDrunk(true);
        }
        return null;
    }

    @Override
    public String toString() {
        return "酒";
    }
}
