package sanguosha.cards.equipments.weapons;

import sanguosha.cards.Color;
import sanguosha.cards.equipments.Weapon;

public class CiXiongShuangGuJian extends Weapon {

    public CiXiongShuangGuJian(Color color, int number) {
        super(color, number, 2);
    }

    @Override
    public Object use() {
        String choice = getTarget().chooseNoNull(
                "you throw a card", "he draws a card");
        if (choice.equals("he draws a card") || getTarget().requestCard(null) == null) {
            getSource().drawCard();
        }
        return null;
    }

    @Override
    public String toString() {
        return "雌雄双股剑";
    }
}
