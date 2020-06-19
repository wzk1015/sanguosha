package sanguosha.cards.strategy;

import sanguosha.cards.Color;
import sanguosha.cards.Strategy;

public class JueDou extends Strategy {
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
                    getTarget().hurt(getThisCard(), getSource(),1);
                    break;
                }
                if ((!getTarget().hasWuShuang() && getSource().requestSha() == null) ||
                    getTarget().hasWuShuang() && (getSource().requestSha() == null ||
                            getSource().requestSha() == null)) {
                    getSource().hurt(getThisCard(), getTarget(),1);
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
}
