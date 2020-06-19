package sanguosha.cards.equipments.shields;

import sanguosha.cards.Color;
import sanguosha.cards.equipments.Shield;
import sanguosha.cardsheap.CardsHeap;

public class BaGuaZhen extends Shield {
    public BaGuaZhen(Color color, int number) {
        super(color, number);
    }

    @Override
    public Object use() {
        return CardsHeap.judge(getSource()).isRed();
    }

    @Override
    public String toString() {
        return "八卦阵";
    }
}
