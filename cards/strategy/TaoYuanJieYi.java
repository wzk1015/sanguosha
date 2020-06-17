package cards.strategy;

import cards.Color;
import cards.Strategy;
import manager.GameManager;
import people.Person;

public class TaoYuanJieYi extends Strategy {
    public TaoYuanJieYi(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        for (Person p : GameManager.getPlayers()) {
            if (!gotWuXie(p)) {
                p.recover(1);
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "桃园结义";
    }
}
