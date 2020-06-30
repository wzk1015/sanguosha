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

    @Override
    public String details() {
        return "每当你使用【杀】指定一名异性的目标角色后，你可以令其选择一项：1.弃置一张手牌；2.令你摸一张牌。";
    }
}
