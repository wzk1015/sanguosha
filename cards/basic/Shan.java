package cards.basic;

import cards.BasicCard;
import cards.Color;

public class Shan extends BasicCard {
    public Shan(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        return true;
    }

    @Override
    public String toString() {
        return "闪";
    }
}
