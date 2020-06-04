package cards.strategy;

import cards.Color;
import cards.Strategy;

public class WuZhongShengYou extends Strategy {

    public WuZhongShengYou(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        if (!gotWuXie()) {
            getTarget().drawCards(2);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "无中生有";
    }
}
