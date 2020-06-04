package cards.strategy;

import cards.Card;
import cards.Color;
import cards.Strategy;
import manager.IO;

import java.util.ArrayList;

public class GuoHeChaiQiao extends Strategy {
    public GuoHeChaiQiao(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        if (!gotWuXie()) {
            ArrayList<Card> cards = IO.printAllCards(getTarget());
            Card c = getSource().chooseCard(cards);
            getTarget().loseCard(c);
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
