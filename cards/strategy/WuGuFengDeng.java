package cards.strategy;

import cards.Color;
import cards.Strategy;
import manager.GameManager;

public class WuGuFengDeng extends Strategy {
    public WuGuFengDeng(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        GameManager.wuGuFengDeng();
        return true;
    }

    @Override
    public String toString() {
        return "五谷丰登";
    }
}
