package cards.strategy;

import cards.Color;
import cards.Strategy;
import manager.GameManager;

public class TaoYuanJieYi extends Strategy {
    public TaoYuanJieYi(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        GameManager.taoYuanJieYi();
        return true;
    }

    @Override
    public String toString() {
        return "桃园结义";
    }
}
