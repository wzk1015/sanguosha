package sanguosha.cards.basic;

import sanguosha.cards.BasicCard;
import sanguosha.cards.Color;

public class Jiu extends BasicCard {
    public Jiu(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        getTarget().setDrunk(true);
        return null;
    }

    @Override
    public String toString() {
        return "酒";
    }

    @Override
    public String help() {
        return "使用时机：出牌阶段或当你处于濒死状态时\n" +
                "使用目标：你\n" +
                "作用效果：目标角色于此回合内使用的下一张【杀】的伤害值+1（未濒死时，每回合限一次）" +
                "或你回复1 点体力（濒死时，不限次数）。";
    }
}
