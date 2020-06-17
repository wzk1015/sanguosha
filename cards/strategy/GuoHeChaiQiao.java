package cards.strategy;

import cards.Card;
import cards.Color;
import cards.Strategy;

public class GuoHeChaiQiao extends Strategy {
    public GuoHeChaiQiao(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        if (!gotWuXie(getTarget())) {
            Card c = getSource().chooseTargetAllCards(getTarget());
            getTarget().loseCard(c, true);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "过河拆桥";
    }

    @Override
    public boolean needChooseTarget() {
        return true;
    }
}
