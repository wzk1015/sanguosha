package cards.strategy;

import cards.Color;
import cards.Strategy;

public class WuXieKeJi extends Strategy {
    public WuXieKeJi(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        return true;
    }

    @Override
    public String toString() {
        return "无懈可击";
    }
}
