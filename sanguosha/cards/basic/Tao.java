package sanguosha.cards.basic;

import sanguosha.cards.BasicCard;
import sanguosha.cards.Color;

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

    @Override
    public String help() {
        return "使用时机：出牌阶段或一名角色濒死时。\n" +
                "使用目标：你（出牌阶段）或处于濒死状态的角色。\n" +
                "作用效果：目标角色回复1 点体力。";
    }
}
