package sanguosha.cards.strategy;

import sanguosha.cards.Color;
import sanguosha.cards.Strategy;

public class WuZhongShengYou extends Strategy {

    public WuZhongShengYou(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        if (!gotWuXie(getTarget())) {
            getTarget().drawCards(2);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "无中生有";
    }

    @Override
    public String details() {
        return "出牌阶段，对你使用。你摸两张牌。";
    }
}
