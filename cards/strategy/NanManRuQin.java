package cards.strategy;

import cards.Color;
import cards.Strategy;
import manager.GameManager;

public class NanManRuQin extends Strategy {
    public NanManRuQin(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        GameManager.nanManRuQin(this, getSource());
        return true;
    }

    @Override
    public String toString() {
        return "南蛮入侵";
    }
}
