package cards.equipments.shields;

import cards.Color;
import cards.equipments.Shield;

public class TengJia extends Shield {
    public TengJia(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        return null;
    }

    @Override
    public String toString() {
        return "藤甲";
    }
}
