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

    @Override
    public String details() {
        return "每当你需要使用或打出一张【闪】时，你可以进行一次判定，若判定结果为红色，视为你使用或打出了一张【闪】。";
    }
}
