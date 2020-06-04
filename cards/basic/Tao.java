package cards.basic;

import cards.BasicCard;
import cards.Color;

public class Tao extends BasicCard {

    public Tao(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        getTarget().recover(1);
        return true;
    }

    @Override
    public String toString() {
        return "桃";
    }
}
