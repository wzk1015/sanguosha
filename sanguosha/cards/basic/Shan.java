package sanguosha.cards.basic;

import sanguosha.cards.BasicCard;
import sanguosha.cards.Color;

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

    @Override
    public String help() {
        return "使用时机：以你为目标的【杀】生效前。\n" +
                "使用目标：以你为目标的【杀】。\n" +
                "作用效果：抵消此【杀】。";
    }
}
