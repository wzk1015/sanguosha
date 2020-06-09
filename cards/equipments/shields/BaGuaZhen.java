package cards.equipments.shields;

import cards.Color;
import cards.equipments.Shield;
import cardsheap.CardsHeap;

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
