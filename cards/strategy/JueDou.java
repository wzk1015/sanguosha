package cards.strategy;

import cards.Card;
import cards.Color;
import cards.Strategy;

public class JueDou extends Strategy {
    private Card thisCard = this;

    public JueDou(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        if (!gotWuXie(getTarget())) {
            getSource().jueDouBegin();
            getTarget().jueDouBegin();
            while (true) {
                if ((!getSource().hasWuShuang() && getTarget().requestSha() == null) ||
                    getSource().hasWuShuang() && (getTarget().requestSha() == null ||
                            getTarget().requestSha() == null)) {
                    getTarget().hurt(thisCard, getSource(),1);
                    break;
                }
                if ((!getTarget().hasWuShuang() && getSource().requestSha() == null) ||
                    getTarget().hasWuShuang() && (getSource().requestSha() == null ||
                            getSource().requestSha() == null)) {
                    getSource().hurt(thisCard, getTarget(),1);
                    break;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "决斗";
    }

    @Override
    public boolean needChooseTarget() {
        return true;
    }

    public void setThisCard(Card thisCard) {
        this.thisCard = thisCard;
    }
}
