package cards.strategy;

import cards.Card;
import cards.Color;
import cards.Strategy;
import manager.GameManager;

import java.util.ArrayList;

public class WanJianQiFa extends Strategy {
    private ArrayList<Card> thisCard = new ArrayList<>();

    public WanJianQiFa(Color color, int number) {
        super(color, number);
        thisCard.add(this);
    }

    public void setThisCard(ArrayList<Card> thisCard) {
        this.thisCard = thisCard;
    }

    public void setThisCard(Card thisCard) {
        ArrayList<Card> cs = new ArrayList<>();
        cs.add(thisCard);
        this.thisCard = cs;
    }

    @Override
    public Object use() {
        GameManager.wanJianQiFa(thisCard, this, getSource());
        return true;
    }

    @Override
    public String toString() {
        return "万箭齐发";
    }
}
