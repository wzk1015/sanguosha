package cards.strategy;

import cards.Color;
import cards.Strategy;
import manager.GameManager;

public class WanJianQiFa extends Strategy {
    public WanJianQiFa(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        GameManager.wanJianQiFa(getSource());
        return true;
    }

    @Override
    public String toString() {
        return "万箭齐发";
    }
}
